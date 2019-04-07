package com.fulln.me.web.config.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: api
 * @description: 不被拦截的URl
 * @author: fulln
 * @create: 2018-10-25 15:16
 * @Version： 0.0.1
 **/
@Data
@Component
@ConfigurationProperties(prefix = "com.fulln.api.urls")
public class NonBlockingUrlConfig {

    private List<String> nonBlockingUrl;

    private String successUrl;

    private String unauthorizedUrl;

    private String loginUrl;

    private String logoutUrl;

}
