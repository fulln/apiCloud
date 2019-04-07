package com.fulln.me.web.config.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author fulln
 * @program listenMemory
 * @description 配置的邮件参数
 * @Date 2018-11-22 16:06
 * @Version 0.0.1
 **/
@Data
@Component
@ConfigurationProperties(prefix = "com.fulln.api.mail")
public class MailConfig {

    private String host;
    private String account;
    private String password;
    private String isAuth;
    private String outTime;
    private String imgPath;

}
