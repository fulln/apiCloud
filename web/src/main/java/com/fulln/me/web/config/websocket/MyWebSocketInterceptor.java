package com.fulln.me.web.config.websocket;


import com.fulln.me.web.service.log.ILogOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;


/**
 * @author fulln
 * @version 0.0.1
 * @program api
 * @description ws拦截处理
 * @date 2019-01-23 11:56
 **/
@Slf4j
public class MyWebSocketInterceptor implements HandshakeInterceptor {



    private ILogOperationService operationService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
      log.info("在处理前执行");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

        log.info("在处理后执行");
//        if (serverHttpRequest instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
//            HttpServletRequest httpRequest = servletRequest.getServletRequest();
//            SysUserBasic user = (SysUserBasic) httpRequest.getSession().getAttribute("currentUser");
//
//            LogOperationInfo info = new LogOperationInfo();
//            //封装实体
//            info.setRequestType(httpRequest.getMethod());
//            info.setOperationUrl(httpRequest.getRequestURL().toString());
//            info.setRequestHead("None");
//            info.setOperationUser(user.getUserName());
//
//            info.setUserIp(RequestIpUtil.getIpAddress(httpRequest));
//            info.setOperationParams("None");
//            info.setOperationMethod("webSocket");
//            operationService.save(info);
//        }


    }


    public ILogOperationService getOperationService() {
        return operationService;
    }

    @Autowired
    public void setOperationService(ILogOperationService operationService) {
        this.operationService = operationService;
    }
}
