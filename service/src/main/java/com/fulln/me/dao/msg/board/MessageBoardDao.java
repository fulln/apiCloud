package com.fulln.me.dao.msg.board;

import com.fulln.me.api.common.MongoDb.MongoHelper;
import com.fulln.me.api.common.entity.PageResult;
import com.fulln.me.api.model.msg.board.MessageBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
        mongoHelper.add(messageBoard);
    }

    public PageResult<MessageBoard> findIntersective(MessageBoard messageBoard) {
        Criteria criteriaDefinition = Criteria.where("duration").is(messageBoard.getCreateBy());
        Query query = new Query(criteriaDefinition);
        return mongoHelper.pageQuery(query, MessageBoard.class, messageBoard.getPageSize(), messageBoard.getPageNo());
    }

}
