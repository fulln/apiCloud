package com.fulln.me.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @AUthor: fulln
 * @Description:  默认的head
 * @Date : Created in  13:37  2018/11/11.
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.fulln.api.defaulthead")
public class DefaultHeadConfig {

    private String userName;
    private String token;
    private Integer sessionOutTime;
}
