package com.fulln.me.web.config.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author fulln
 * @version 0.0.1
 * @program api
 * @description websocket推送类
 * @date 2019-01-23 10:15
 **/
@Configuration
@EnableWebSocket
public class WebSocketConfig  {

    /**
     * implements WebSocketConfigurer
     * @return
     */

//    @Bean
//    public ServletServerContainerFactoryBean createWebSocketContainer() {
//        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
//        container.setMaxTextMessageBufferSize(8192);
//        container.setMaxBinaryMessageBufferSize(8192);
//        return container;
//    }


//    @Bean
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
//        webSocketHandlerRegistry.addHandler(myHandler(), "/websocket").addInterceptors(new MyWebSocketInterceptor()).setAllowedOrigins("*");
//    }

//    @Bean
//    public WebSocketHandler myHandler() {
//        return new MyHandler();
//    }


    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}

