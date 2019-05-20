package com.fulln.me.thread;


import com.fulln.me.api.common.enums.GlobalEnums;
import com.fulln.me.api.common.enums.RegixEnums;
import com.fulln.me.api.common.threadconfig.AbstractThreadStartFactory;
import com.fulln.me.api.common.utils.DateUtil;
import com.fulln.me.api.common.utils.FileUtil;
import com.fulln.me.api.model.system.SysArticleInfo;
import com.fulln.me.api.model.system.cloums.ArticleStatusEnums;
import com.fulln.me.dao.system.SysArticleInfoDao;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Date;
import java.util.Map;


/**
 * @program: api
 * @description: 本地读取文件线程
 * @author: fulln
 * @create: 2018-11-13 17:43
 * @Version： 0.0.1
 **/
@Slf4j
public class FileReadTask extends AbstractThreadStartFactory {

    /**
     * 需要执行的方法和参数
     */
    private File path;
    private final SysArticleInfoDao sysArticleInfoDao;


    public FileReadTask(File path, SysArticleInfoDao sysArticleInfoDao) {
        this.path = path;
        this.sysArticleInfoDao = sysArticleInfoDao;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Object call() throws Exception {

        StringBuilder sb = FileUtil.readFile(path);

        SysArticleInfo info = new SysArticleInfo();

        info.setUpdateTime(DateUtil.getNowTimeStamp());
        info.setArticleFilePath(path.getAbsolutePath());
        info.setArticlePushStatus(ArticleStatusEnums.PUSHED);
        info.setArticleFileName(path.getName().replace(".md",""));
        info.setCustNo("A");
        info.setArticleContent(sb != null ? sb.toString() : null);

        String type = RegixEnums.ARTICLE_HEAD_PATTERN.getContentResult(sb + "");

        @SuppressWarnings("unchecked")
        Map<String, Object> maps = (Map<String, Object>) FileUtil.getYamlMap(type);
        info.setArticleName(maps.get("title")+"");
        info.setArticleType(maps.get("categories")+"");
        String tag = maps.get("tags").toString();
        info.setArticleTag(tag.substring(1,tag.length()-1));

        try {
            if(maps.get("date") instanceof Date){
                info.setCreateTime(((Date) maps.get("date")).getTime());
            }else{
                info.setCreateTime(DateUtil.getTimeStamp(maps.get("date")+"","yyyy-MM-dd HH:mm:ss"));
            }
        }catch (Exception e){
            log.error("日期转码异常",e);
            info.setCreateTime(DateUtil.getNowTimeStamp());
        }

        info.setRemarks("AutoScan");
            sysArticleInfoDao.insertOrUpdate(info);
        return  GlobalEnums.READ_SUCCESS.results();
    }
}
