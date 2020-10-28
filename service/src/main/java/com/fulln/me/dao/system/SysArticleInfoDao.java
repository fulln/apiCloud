package com.fulln.me.dao.system;


import me.fulln.base.model.system.DTO.SysArticleInfoDTO;
import me.fulln.base.model.system.SysArticleInfo;
import com.fulln.me.config.basic.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: api
 * @description: 文章数据交互层
 * @author: fulln
 * @create: 2018-11-13 17:12
 * @Version： 0.0.1
 **/
public interface SysArticleInfoDao extends MyMapper<SysArticleInfo> {


    List<SysArticleInfo> findAll(SysArticleInfoDTO dto);

    /**
     * 根据id更新
     * @param info
     * @return
     */
    void updateById(SysArticleInfo info);

    /**
     * 更新或新增
     * @param info
     */
    void insertOrUpdate(SysArticleInfo info);

    /**
     * 查询文章名称
     * @return
     */
    @Select("select article_file_name from sys_article_info  where article_push_status !=2 ")
    List<String> selectAllName();
}
