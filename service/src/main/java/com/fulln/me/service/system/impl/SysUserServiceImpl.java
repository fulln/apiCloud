package com.fulln.me.service.system.impl;


import me.fulln.base.common.constant.ConstantAll;
import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.common.enums.GlobalEnums;
import me.fulln.base.common.exception.ServiceException;
import me.fulln.base.model.email.EmailEntity;
import me.fulln.base.model.user.SysUserBasic;
import com.fulln.me.config.redis.RedisUtil;
import com.fulln.me.dao.system.SysUserDao;
import com.fulln.me.service.basic.IThreadStartService;
import com.fulln.me.service.system.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @program: api
 * @description: 用户接口层实现
 * @author: fulln
 * @create: 2018-09-27 17:59
 * @Version： 0.0.1
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    private SysUserDao userDao;

    @Autowired
    private IThreadStartService threadStartService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据用户名查找用户
     *
     * @param name
     * @return
     */
    @Override
    public SysUserBasic selectByUsername(String name) {
        try {

            SysUserBasic userBasic = new SysUserBasic();
            userBasic.setUserName(name);
            return userDao.selectOne(userBasic);
        } catch (Exception e) {
            log.error("根据用户名查找异常", e);
            return null;
        }
    }

    /**
     * 根据名称更新
     *
     * @param name
     * @param count
     * @return
     */
    @Override
    public boolean updateLoginFail(String name, int count) {
        return userDao.updateLoginFail(name, count) > 0;
    }

    /**
     * 更新
     *
     * @param currentUser
     * @return
     */
    @Override
    public GlobalResult Update(SysUserBasic currentUser) {
        try {
            userDao.updateByPrimaryKeySelective(currentUser);
            return GlobalEnums.UPDATE_SUCCESS.results();
        } catch (Exception e) {
            log.error("根据用户名更新", e);
            return GlobalEnums.UPDATE_ERROR.results();
        }
    }


    @Override
    public GlobalResult add(SysUserBasic sysUserBasic) {
        try {
            checkUserParams(sysUserBasic);
            sysUserBasic.setCreateDate(DateUtil.getNowTimeStamp());
            sysUserBasic.setUpdateDate(DateUtil.getNowTimeStamp());
            sysUserBasic.setUserSalt(MD5util.getSalt());
            sysUserBasic.setUserPass(MD5util.getMd5Hash(sysUserBasic.getUserPass(), sysUserBasic.getUserSalt()));
            sysUserBasic.setRoleId(1);
            sysUserBasic.setIsEmailConfirmed(1);
            int insert = userDao.insert(sysUserBasic);
            if (insert > 0) {
                return GlobalEnums.EMAIL_SUCCESS.results();
            } else {
                return GlobalEnums.EMAIL_FAIL.results();
            }
        } catch (ServiceException e) {
            return GlobalEnums.EMPTY_PARAMETER.results(e.getMessage());
        } catch (Exception e) {
            log.error("新增用户", e);
            return GlobalEnums.INSERT_ERROR.results();
        }
    }

    private void checkUserParams(SysUserBasic sysUserBasic) {
        CheckParamsUtil.checkNull("用户", sysUserBasic);
        CheckParamsUtil.checkNull("用户邮箱", sysUserBasic.getEmail());
        CheckParamsUtil.checkNull("用户手机", sysUserBasic.getMobile());
        SysUserBasic userBasic = this.selectByUsername(sysUserBasic.getUserName());
        if (userBasic != null) {
            throw new ServiceException("当前用户名已经被注册");
        }
    }

    @Override
    public GlobalResult emailCheckForRegister(SysUserBasic sysUserBasic) {
        try {
            checkUserParams(sysUserBasic);
            EmailEntity emailEntity = new EmailEntity();
            emailEntity.setReceiver(sysUserBasic.getEmail());
            emailEntity.setSubject(ConstantAll.EMAIL_FOR_REIGISIT_SUBJECT);
            String registerCode = AesUtil.aesEncrypt(ConstantAll.EMAIL_FOR_REIGISIT_RECIVE_USER + sysUserBasic.getUserName(), ConstantAll.EMAIL_FOR_REIGISIT_SEND_SALT);
            emailEntity.setText(String.format("您正在fulln.me上注册用户,请点击以下链接确认是本人,此链接5分钟内有效,如果不是本人操作请忽略当前邮件</br></br>http://localhost:8082/web/registered/%s", registerCode));
            threadStartService.sendEmail(emailEntity);
            if (!redisUtil.hasKey(registerCode)) {
                redisUtil.set(registerCode, GsonUtil.gsonString(sysUserBasic), ConstantAll.REDIS_REGISITER_REMINE_TIME);
            }
            return GlobalEnums.EMAIL_SUCCESS.results();
        } catch (ServiceException e) {
            return GlobalEnums.REGISTER_FAIL.results(e.getMessage());
        } catch (Exception e) {
            log.error("当前发送邮件出现异常", e);
            return GlobalEnums.EMAIL_FAIL.results("当前发送邮件出现异常");
        }
    }

    /**
     * 邮件返回值之后获取当前的值进行校验
     *
     * @param registerCode
     * @return
     */
    @Override
    public GlobalResult checkRegistEmailBack(String registerCode) {
        try {
            SysUserBasic sysUserBasic = GsonUtil.gsonToBean(redisUtil.get(registerCode).toString(), SysUserBasic.class);
            GlobalResult add = add(sysUserBasic);
            if (add.getCode() > 0) {
                return GlobalEnums.REGISTER_SUCCESS.results();
            } else {
                return add;
            }
        } catch (Exception e) {
            return GlobalEnums.REGISTER_FAIL.results();
        }
    }
}
