package com.fulln.me.web.config.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: WebSocketServer
 * @description: ws连接端
 * @author: fulln
 * @Date 11:02 2019/1/23 0023
 * @Version： 0.0.1
 **/
@Slf4j
@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {


    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        //加入set中
        webSocketSet.add(this);
        //在线数加1
        addOnlineCount();
        log.info("有新窗口开始监听:" + sid + ",当前在线人数为" + getOnlineCount());
        this.sid = sid;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + sid + "的信息:" + message);
        //群发消息
        try {
            sendInfo("收到消息", null);
        } catch (IOException e) {
            log.error("消息接收", e);
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误", error);
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {

        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        String content = "ALL";
        if (!StringUtils.isEmpty(sid)) {
            content = sid;
        }
        log.info("推送消息到窗口 " + content + "，推送内容:" + message);
        webSocketSet.stream()
                .filter(chat -> {
                    if (StringUtils.isEmpty(sid)) {
                        return true;
                    } else {
                        return chat.session.getPathParameters().get("currentUserId").equals(sid);
                    }
                })
                .filter(server -> server.session.isOpen())
                .forEach(chat -> {
                    try {
                        chat.sendMessage(message);
                    } catch (IOException e) {
                        log.error("消息发送异常", e);
                    }
                });
    }

    private int getOnlineCount() {
        return this.onlineCount.get();
    }

    private void addOnlineCount() {
        this.onlineCount.incrementAndGet();
    }

    private void subOnlineCount() {
        this.onlineCount.decrementAndGet();
    }
}
