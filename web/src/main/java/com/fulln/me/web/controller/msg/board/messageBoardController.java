package com.fulln.me.web.controller.msg.board;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.msg.board.MessageBoard;
import com.fulln.me.web.config.annotation.userMessage;
import com.fulln.me.web.config.base.method.BaseController;
import com.fulln.me.web.service.msg.board.MessageBoardService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 留言板实体
 * @date 2019/5/22 23:09
 **/
@Slf4j
@RestController
@RequestMapping("/message")
@Api(tags = "留言板api")
public class messageBoardController extends BaseController {

    @Resource
    private MessageBoardService messageBoardService;

    @PostMapping("/list")
    public GlobalResult findAll(){
        return messageBoardService.allList();
    }

    @PostMapping("/insert")
    @userMessage("board")
    public GlobalResult Insert(MessageBoard board){
        return messageBoardService.insert(board);
    }

}
