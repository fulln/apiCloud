package com.fulln.me.web.config.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

/**
 * @author fulln
 * @version 0.0.1
 * @program ListenWebCloud
 * @description 在线文档配置类
 * @date 2019-01-07 10:15
 **/
@Configuration
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {

        Predicate<RequestHandler> predicate = input -> {
            Class<?> declaringClass = input.declaringClass();
            // 被注解的类
            if (declaringClass.isAnnotationPresent(RestController.class))
                return true;
            // 被注解的方法
            if (input.isAnnotatedWith(ResponseBody.class))
                return true;
            return false;
        };

        return new Docket(DocumentationType.SWAGGER_2).groupName("innerApi").apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select().apis(RequestHandlerSelectors.basePackage("com.fulln.me.web.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        // 大标题
        return new ApiInfoBuilder().title("api接口服务")
                // 版本
                .version("1.0")
                .build();
    }




}
