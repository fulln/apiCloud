package com.fulln.me.web.config.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * @author: fulln
 * @program: MyHandler
 * @description: 处理器
 * @Date 10:46 2019/1/23 0023
 * @Version： 0.0.1
 **/
@Slf4j
public class MyHandler implements WebSocketHandler {


    private static ConcurrentLinkedQueue<WebSocketSession> lists = new ConcurrentLinkedQueue<>();


    /**
     * 连接建立后
     *
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        log.error("当前已经建立连接");
        lists.add(webSocketSession);
    }


    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        TextMessage msg = new TextMessage(webSocketMessage.getPayload().toString());
        //给指定用户发消息
        sendMessage(webSocketSession.getAttributes().get("currentUserId").toString(),msg);

    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        TextMessage msg = new TextMessage("connect time out...connect closed");
        //给所有用户群发消息
        sendMessage(null,msg);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        lists.remove(webSocketSession);
        log.info("安全退出了系统");

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 发送消息给用户
     */
    public void sendMessage(String userId, TextMessage message) {
        lists.stream()
                .filter(chat -> {
                    if(StringUtils.isEmpty(userId)){
                        return true;
                    }else{
                        return chat.getAttributes().get("currentUserId").toString().equals(userId);
                    }
                })
                .filter(WebSocketSession::isOpen)
                .forEach(chat -> {
                    try {
                        chat.sendMessage(message);
                    } catch (IOException e) {
                        log.error("消息发送异常", e);
                    }
                });
    }



}
