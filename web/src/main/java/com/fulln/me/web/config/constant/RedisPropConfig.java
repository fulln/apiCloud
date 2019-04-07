package com.fulln.me.web.config.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: api
 * @description: redis配置文件
 * @author: fulln
 * @create: 2018-10-25 15:49
 * @Version： 0.0.1
 **/
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisPropConfig {

    private String host;
    private Integer port;
    private String password;
    private Integer timeout;

}
