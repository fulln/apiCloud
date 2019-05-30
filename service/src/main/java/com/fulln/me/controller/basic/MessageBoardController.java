package com.fulln.me.controller.basic;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.msg.board.MessageBoard;
import com.fulln.me.service.msg.board.IMessageBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 留言板控制层
 * @date 2019/5/24 23:15
 **/
@Slf4j
@RequestMapping("/msg/board")
@RestController
public class MessageBoardController {

    @Autowired
    private IMessageBoardService messageBoardService;

    @PostMapping("/findAll")
    public GlobalResult getAll(){
        return messageBoardService.findAll();
    }

    @PostMapping("/add")
    public GlobalResult saveOrUpdateByUser(@RequestBody MessageBoard board){
        return messageBoardService.insertOrUpdate(board);
    }

    @PostMapping("/findByCondition")
    public GlobalResult findByCondition(@RequestBody MessageBoard board){
        return messageBoardService.findByCondition(board);
    }


}
