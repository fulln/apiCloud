package com.fulln.me.service.system.impl;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.system.SysPermission;
import com.fulln.me.api.service.system.ISysPermissionService;
import com.fulln.me.dao.system.SysPermissionDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fulln
 * @description es的相关模板
 * 只实现查询部分(关联mysql 实现读写分离)
 * @date : Created in  0:29  2018/10/12.
 */
@Transactional(rollbackFor = Exception.class)
@Slf4j
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

    @Resource
    private SysPermissionDao permissionDao;

    @Override
    public List<SysPermission> loadUserResources(Integer id) {
        return permissionDao.findByRole(id);
    }


    @Override
    public List<SysPermission> queryAll() {
        return permissionDao.queryAll();
    }

    /**
     * 新增
     *
     * @param permission
     * @return
     */
    @Override
    public GlobalResult save(SysPermission permission) {
        return null;
    }

//    @Override
//    public GlobalResult save() {
//        SysPermissionDTO permission = new SysPermissionDTO();
//        permission.setPermissionId(23L);
//        permission.setId(4L);
//        permission.setResourceUrl("test");
//        permission.setPermissionResourceName("1234");
//        permission.setCreateBy("admin");
//        permission.setUpdateBy("admin");
//        permission.setResourceType(1);
//        permission.setCreateDate(DateUtil.getNowTimeStamp());
//        permission.setUpdateDate(DateUtil.getNowTimeStamp());
//        esQueryDao.save(permission);
//        return GlobalEnums.INSERT_SUCCESS.results();
//    }
//
//
//    @Override
//    public GlobalResult delete(long id) {
//        esQueryDao.deleteById(id);
//        return GlobalEnums.DELETE_SUCCESS.results();
//    }
//
//    @Override
//    public GlobalResult update(Long id, String name, String description) {
//        SysPermissionDTO permission = new SysPermissionDTO();
//        permission.setPermissionId(id);
//        esQueryDao.save(permission);
//        return GlobalEnums.UPDATE_SUCCESS.results();
//    }
//
//
//    @Override
//    public GlobalResult getOne(Long id) {
//        Iterable<SysPermissionDTO> permission = esQueryDao.findAllById(Collections.singletonList(id));
//        return GlobalEnums.QUERY_SUCCESS.results(permission);
//    }
//
//    /**
//     * 单一条件模糊查询,使用 springDataJpa
//     *
//     */
//    @Override
//    public GlobalResult findbyname(int pageNumber, int pageSize, String searchContent) {
//        Page<SysPermissionDTO> records = esQueryDao
//                .findByPermissionResourceNameOrderByPermissionIdDesc(
//                        searchContent,PageRequest.of(pageNumber,pageSize));
//            return GlobalEnums.QUERY_SUCCESS.results(records);
//    }
//
//    /**
//     * 分页查询
//     * @param pageNumber
//     * @param pageSize
//     * @param searchContent
//     * @return
//     */
//    @Override
//    public GlobalResult findname(int pageNumber, int pageSize, String searchContent) {
//
//        QueryBuilder orderQuery = QueryBuilders.boolQuery()
//                .must(QueryBuilders.matchQuery("permissionResourceName", searchContent));
//
//        SearchQuery searchQuery =
//                new NativeSearchQueryBuilder()
//                        .withSearchType(SearchType.DEFAULT)
//                        .withPageable(PageRequest.of(pageNumber,pageSize))
//                        .withQuery(orderQuery).build();
//
//        Page<SysPermissionDTO> page = esQueryDao.search(searchQuery);
//
//        if (page.hasContent()) {
//            return GlobalEnums.QUERY_SUCCESS.results(page);
//        } else {
//            return GlobalEnums.QUERY_EMPTY.results();
//        }
//    }


}
