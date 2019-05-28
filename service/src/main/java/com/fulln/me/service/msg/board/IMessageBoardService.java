package com.fulln.me.service.msg.board;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.msg.board.MessageBoard;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 留言板业务层
 * @date 2019/5/24 22:53
 **/
public interface IMessageBoardService {

    /**
     * 查询所有的留言消息
     * @param messageBoard
     * @return
     */
    GlobalResult findAll();

    /**
     * 新增或更新一条消息
     * @param messageBoard
     * @return
     */
    GlobalResult insertOrUpdate(MessageBoard messageBoard);

    GlobalResult findByCondition(MessageBoard messageBoard);
}
