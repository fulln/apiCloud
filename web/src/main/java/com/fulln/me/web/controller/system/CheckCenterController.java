package com.fulln.me.web.controller.system;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.web.config.enums.GlobalEnums;
import com.fulln.me.web.config.websocket.WebSocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/checkcenter")
public class CheckCenterController {



    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public GlobalResult pushToWeb(@PathVariable String cid, String message) {
        try {
            WebSocketServer.sendInfo(message, cid);
        } catch (IOException e) {
            e.printStackTrace();
            return GlobalEnums.QUERY_FAIL.results(cid + "#" + e.getMessage());
        }
        return GlobalEnums.QUERY_SUCCESS.results(cid);
    }
}