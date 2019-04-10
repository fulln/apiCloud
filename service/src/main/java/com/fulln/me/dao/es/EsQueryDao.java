package com.fulln.me.dao.es;


import com.fulln.me.api.model.system.DTO.SysPermissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author fulln
 * @program api
 * @description es查询测试
 * @Date 2018-12-27 10:03
 * @Version 0.0.1
 * extends ElasticsearchRepository<SysPermissionDTO, Long>
 **/
public interface EsQueryDao {

    /**
     * @param pageable 分页
     * @return
     * @Param permissionResourceName 查询的名称
     */
    Page<SysPermissionDTO> findByPermissionResourceNameOrderByPermissionIdDesc(String permissionResourceName,
                                                                               Pageable pageable);

    /**
     * 模糊查询
     *
     * @param permissionResourceName
     * @return
     */
    List<SysPermissionDTO> findByPermissionResourceNameLikeOrderByPermissionIdDesc(String permissionResourceName);
}
