package com.fulln.me.service.search.impl;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.enums.pythonEnums;
import com.fulln.me.api.model.search.UserTorrents;
import com.fulln.me.config.enums.GlobalEnums;
import com.fulln.me.dao.search.SearchDao;
import com.fulln.me.service.search.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.fulln.me.api.common.utils.DateUtil.getNowTimeStamp;
import static com.fulln.me.api.common.utils.PythonRun.usePython;


/**
 * @AUthor: fulln
 * @Description: 搜索业务实现层
 * @Date : Created in  21:12  2018/9/1.
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SearchServiceImpl implements SearchService {

    @Resource
    private SearchDao searchDao;

    @Cacheable(value = "search-findAll")
    @Override
    public GlobalResult findAll(String text) {
        try {
            UserTorrents queryUser = new UserTorrents();
            queryUser.setTorrentType(text);
            queryUser.setCreateTime(getNowTimeStamp() - 24 * 60 * 60000);
            List<UserTorrents> li = searchDao.findAll(queryUser);
            //当数据库查询不到当前的值的时候
            if (li.size() == 0) {
                //启动脚本去查询
                List<String> list = usePython(pythonEnums.SEARCH_TORRENT.getAll(text));
                if (list.size() == 0) {
                    return GlobalEnums.QUERY_EMPTY.results();
                }
                list.forEach(torrent -> {
                    UserTorrents user = new UserTorrents();
                    user.setTorrentData(torrent);
                    user.setTorrentType(text);
                    user.setCreateBy("admin");
                    user.setCreateTime(getNowTimeStamp());
                    user.setUpdateBy("admin");
                    user.setUpdateTime(getNowTimeStamp());
                    li.add(user);
                });
                searchDao.insertAll(li);
                return GlobalEnums.QUERY_SUCCESS.results(li);
            } else {
                return GlobalEnums.QUERY_SUCCESS.results(li);
            }
        } catch (Exception e) {
            log.error("查询失败", e);
            return GlobalEnums.QUERY_FAIL.results();
        }
    }


}
