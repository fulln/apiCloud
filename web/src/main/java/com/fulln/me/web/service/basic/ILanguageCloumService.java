package com.fulln.me.web.service.basic;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 代码国际化配置接口
 * @date 2019/4/8 21:43
 **/
@FeignClient(value = "${feign.url}")
public interface ILanguageCloumService {

    @GetMapping("/languageCloum/code")
    String getCloums(String code);


    @GetMapping("/languageCloum/codeAndText")
    String getCloums(String code, String text);
}
