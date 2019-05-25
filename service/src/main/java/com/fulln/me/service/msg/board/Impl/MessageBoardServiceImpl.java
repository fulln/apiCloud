package com.fulln.me.service.msg.board.Impl;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.enums.GlobalEnums;
import com.fulln.me.api.common.utils.DateUtil;
import com.fulln.me.api.model.msg.board.MessageBoard;
import com.fulln.me.dao.msg.board.MessageBoardDao;
import com.fulln.me.service.msg.board.IMessageBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 留言板业务层实现
 * @date 2019/5/24 22:54
 **/
@Slf4j
@Service
public class MessageBoardServiceImpl implements IMessageBoardService {

    @Autowired
    private MessageBoardDao messageBoardDao;

    /**
     * 查询所有的留言消息
     * @return
     */
    @Override
    public GlobalResult findAll() {
        List<MessageBoard> boards = messageBoardDao.findAll(MessageBoard.class);
        if (CollectionUtils.isEmpty(boards)) {
            return GlobalEnums.QUERY_EMPTY.results();
        }
        return GlobalEnums.QUERY_SUCCESS.results(boards);
    }

    /**
     * 新增或更新一条消息
     *
     * @param messageBoard
     * @return
     */
    @Override
    public GlobalResult insertOrUpdate(MessageBoard messageBoard) {
        try {
            messageBoard.setCreateTime(DateUtil.getNowTimeStamp());
            messageBoard.setUpdateTime(DateUtil.getNowTimeStamp());
            messageBoardDao.add(messageBoard);
            return GlobalEnums.INSERT_SUCCESS.results();
        } catch (Exception e) {
            log.error("mongoDb 插入异常", e);
            return GlobalEnums.INSERT_ERROR.results("MongoDb插入异常");
        }
    }
}
