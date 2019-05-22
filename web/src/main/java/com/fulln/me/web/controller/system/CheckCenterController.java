package com.fulln.me.web.controller.system;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.enums.GlobalEnums;
import com.fulln.me.web.config.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
@Slf4j
@Controller
@RequestMapping("/checkcenter")
public class CheckCenterController {



    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public GlobalResult pushToWeb(@PathVariable String cid, String message) {
        try {
            WebSocketServer.sendInfo(message, cid);
        } catch (IOException e) {
            log.error("消息发送失败",e);
            return GlobalEnums.QUERY_FAIL.results(cid + "#" + e.getMessage());
        }
        return GlobalEnums.QUERY_SUCCESS.results(cid);
    }
}