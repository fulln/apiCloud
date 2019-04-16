package com.fulln.me.controller.basic;

import com.fulln.me.api.service.basic.ILanguageCloumService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 代码国际化配置接口
 * @date 2019/4/8 21:43
 **/
@RestController
@RequestMapping("/languageCloum")
public class LanguageCloumController{

    @Resource
    private ILanguageCloumService languageCloumService;

    @GetMapping("/code")
    public String getCloums(String code) {
        return languageCloumService.getCloums(code);
    }
    @GetMapping("/codeAndText")
    public String getCloums(String code, String text) {
        return languageCloumService.getCloums(code, text);
    }
}
