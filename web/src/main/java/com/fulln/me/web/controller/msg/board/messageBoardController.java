package com.fulln.me.web.controller.msg.board;

import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.enums.GlobalEnums;
import com.fulln.me.api.common.utils.GsonUtil;
import com.fulln.me.api.model.msg.board.MessageBoard;
import com.fulln.me.web.config.base.method.BaseController;
import com.fulln.me.web.config.websocket.WebSocketServer;
import com.fulln.me.web.service.msg.board.MessageBoardService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

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
    private MessageBoardService messageBoarService;

    @Autowired
    private WebSocketServer webSocketServer;

    @PostMapping("/list")
    public GlobalResult findAll() {
        return messageBoarService.allList();
    }

    @PostMapping("/insert")
    public GlobalResult Insert(MessageBoard board) {
        try {
            GlobalResult result = messageBoarService.insert(board);
            if (result.getCode() > 0) {
                GlobalResult  allResult =  findAll();
                if(allResult.getCode()>0){

                    WebSocketServer.sendInfo(GsonUtil.gsonString(allResult.getDatas()),null);
                }
            }
            return result;
        } catch (IOException e) {
            return GlobalEnums.SYS_ERROR.results();
        }
    }

    @GetMapping("/find")
    public GlobalResult findByCondition(MessageBoard board){
        return messageBoarService.findByCondition(board);

    }

}
