package com.fulln.me.api.common.MongoDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description MongoHelper启动类
 * @date 2019/5/28 23:16
 **/
@Configuration
public class MongoConfig {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public MongoHelper mongoPageHelper() {
        return new MongoHelper(mongoTemplate);
    }

}
