package com.fulln.me.service.system.impl;


import me.fulln.base.common.constant.FileExtensionConfig;
import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.common.enums.GlobalEnums;
import me.fulln.base.common.utils.DateUtil;
import me.fulln.base.common.utils.LinuxSystemUtil;
import me.fulln.base.model.system.DTO.SysArticleInfoDTO;
import me.fulln.base.model.system.SysArticleInfo;
import me.fulln.base.model.system.SysRole;
import me.fulln.base.model.system.cloums.ArticleStatusEnums;
import me.fulln.base.model.user.SysUserBasic;
import com.fulln.me.dao.system.SysArticleInfoDao;
import com.fulln.me.service.basic.IThreadStartService;
import com.fulln.me.service.system.ISysArticleInfoService;
import com.fulln.me.service.system.ISysRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fulln
 * @version 0.0.1
 * @program api
 * @description 文章业务层实现
 * @date 2018-11-13 17:22
 **/
@Slf4j
@Service

public class SysArticleInfoServiceImpl implements ISysArticleInfoService {

    @Value("${com.fulln.api.defaultDownFile}")
    private String path;

    @Resource
    private SysArticleInfoDao sysArticleInfoDao;

    @Resource
    private IThreadStartService threadService;

    @Autowired
    private ISysRoleService roleService;


    @Override
    public GlobalResult findAll(SysArticleInfoDTO info, SysUserBasic user) {
        if (StringUtils.isEmpty(info.getPageNo()) && StringUtils.isEmpty(info.getPageSize())) {
            return GlobalEnums.QUERY_EMPTY.results();
        }
        //查询当前的对应的角色

        GlobalResult result = roleService.findByUserId(user.getUserId().intValue());
        if (result.getCode() < 0) {
            return result;
        }
        SysRole sysUserRole = (SysRole) result.getDatas();
        PageHelper.startPage(info.getPageNo(), info.getPageSize());
        info.setCustNo(sysUserRole.getRoleCode());

        List<SysArticleInfo> li = sysArticleInfoDao.findAll(info);
        PageInfo<SysArticleInfo> pageInfo = new PageInfo<>(li);
        return GlobalEnums.QUERY_SUCCESS.results(pageInfo);
    }

    @Override
    public GlobalResult findOne(SysUserBasic user) {
        ArrayList<String> list = new ArrayList<>();
        //保护性编程
        File file = null;
        if (path != null) {
            file = new File(path);
        }
        if (file != null && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                List<String> fileList = sysArticleInfoDao.selectAllName();
                Arrays.asList(files).forEach(
                        f -> {
                            boolean flag = true;
                            if (fileList.size() != 0) {
                                flag = !fileList.contains(f.getName());
                            }
                            if (!f.isDirectory() && f.getName().endsWith(FileExtensionConfig.FILE_MD) && flag) {
                                GlobalResult s = threadService.fileReader(f);
                                if (s.getCode() < 0) {
                                    list.add(f.getName());
                                }
                            }
                        }
                );
            }
        } else {
            return GlobalEnums.QUERY_EMPTY.results();
        }

        if (list.size() == 0) {
            return GlobalEnums.READ_SUCCESS.results();
        } else {
            return GlobalEnums.READ_ERROR.results(list);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GlobalResult insertOne(SysArticleInfo info, SysUserBasic basic) {
        try {

            GlobalResult result = roleService.findByUserId(basic.getUserId().intValue());
            if (result.getCode() < 0) {
                return result;
            }
            SysRole sysUserRole = (SysRole) result.getDatas();


            String paths = path + File.separator + info.getArticleFileName() + FileExtensionConfig.FILE_DOT + FileExtensionConfig.FILE_MD;
            info.setCustNo(sysUserRole.getRoleCode());
            info.setCreateTime(DateUtil.getNowTimeStamp());
            info.setUpdateTime(DateUtil.getNowTimeStamp());
            info.setArticleFilePath(paths);
            if (info.getArticlePushStatus() == ArticleStatusEnums.PUSHED || info.getArticlePushStatus() == ArticleStatusEnums.SAVED) {
                return GlobalEnums.FILE_EXIST.results();
            } else {
                sysArticleInfoDao.insertSelective(info);
                LinuxSystemUtil.runTask("docker exec -itd blog /bin/bash  hexo new " + info.getArticleFileName());
                threadService.fileCreate(info.getArticleFilePath(), info.getArticleContent());
                return GlobalEnums.INSERT_SUCCESS.results();
            }
        } catch (Exception e) {
            log.error("文章插入异常", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return GlobalEnums.INSERT_ERROR.results();
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GlobalResult updateOne(SysArticleInfo info, SysUserBasic basic) {
        try {
            String paths = path + File.separator + info.getArticleFileName() + FileExtensionConfig.FILE_DOT + FileExtensionConfig.FILE_MD;
            info.setUpdateTime(DateUtil.getNowTimeStamp());
            sysArticleInfoDao.updateById(info);
            if (info.getArticlePushStatus() == ArticleStatusEnums.DELETED) {
                log.error(paths);
                LinuxSystemUtil.runTask("rm -rf  " + paths);
                return GlobalEnums.DELETE_SUCCESS.results();
            } else {
                if (!StringUtils.isEmpty(info.getArticleContent())) {
                    File file = new File(paths);
                    if (file.exists()) {
                        threadService.fileCreate(file.getAbsolutePath(), info.getArticleContent());
                    } else {
                        return GlobalEnums.FILE_NOT_EXIST.results();
                    }
                }
                return GlobalEnums.UPDATE_SUCCESS.results();
            }
        } catch (Exception e) {
            log.error("文章更新异常", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (info.getArticlePushStatus() == ArticleStatusEnums.DELETED) {
                return GlobalEnums.DELETE_ERROR.results();
            } else {
                return GlobalEnums.UPDATE_ERROR.results();
            }
        }
    }
}
