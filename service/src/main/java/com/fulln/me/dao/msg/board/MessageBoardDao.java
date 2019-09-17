package com.fulln.me.dao.msg.board;

import com.fulln.me.api.common.Mongo.MongoHelper;
import com.fulln.me.api.common.entity.PageResult;
import com.fulln.me.api.common.utils.SnowflakeUtils;
import com.fulln.me.api.model.msg.board.MessageBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 留言板MongoDbdao层
 * @date 2019/5/23 23:27
 **/
@Repository
public class MessageBoardDao {

    @Autowired
    private MongoHelper mongoHelper;

    public List<MessageBoard> findAll() {
        return mongoHelper.findAll(MessageBoard.class);
    }

    public void add(MessageBoard messageBoard) {
        messageBoard.setMessageId(new SnowflakeUtils(1L,1L).nextId());
        mongoHelper.add(messageBoard);
    }

    public PageResult<MessageBoard> findIntersective(MessageBoard messageBoard) {
        return  mongoHelper.pageQuery(messageBoard, messageBoard.getPageSize(), messageBoard.getPageNo());
    }

}
