package com.fulln.me.dao.search;


import me.fulln.base.model.search.UserTorrents;
import com.fulln.me.config.basic.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @AUthor: fulln
 * @Description:搜索接口层
 * @Date : Created in  23:34  2018/9/1.
 */
public interface SearchDao extends MyMapper<UserTorrents> {

    @Select("select * from user_torrents where torrent_type = #{torrentType} and create_time > #{createTime}")
    List<UserTorrents> findAll(UserTorrents user);

    void insertAll(List<UserTorrents> list);
}
