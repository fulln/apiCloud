package com.fulln.me.dao.msg.board;

import com.fulln.me.api.model.msg.board.MessageBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    private MongoTemplate mongoTemplate;

    public void save(MessageBoard messageBoard) {
        mongoTemplate.save(messageBoard);
    }

    public <T> List<T> findAll(Class<T> entityClass) {
        return mongoTemplate.findAll(entityClass);
    }

    public <T> void add(T entity) {
        mongoTemplate.insert(entity);
    }

    public List<MessageBoard> findIntersective(MessageBoard messageBoard) {
        Query query = new Query(Criteria.where(""));
        return mongoTemplate.find(query, MessageBoard.class);
    }

}
