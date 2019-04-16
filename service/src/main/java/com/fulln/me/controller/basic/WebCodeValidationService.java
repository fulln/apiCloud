package com.fulln.me.controller.basic;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.service.basic.CodeValidationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: api
 * @description: 接码平台业务层接口
 * @author: fulln
 * @create: 2018-09-21 10:50
 * @Version： 0.0.1
 **/
@RestController
@RequestMapping("/CodeValidate")
public class WebCodeValidationService{

    @Resource
    private CodeValidationService codeValidationService;

    /**
     * 接码平台获取token
     *
     * @return
     */
    @GetMapping("/getPhoneCode")
    public GlobalResult getPhoneCode(String token, String phone) {
        return codeValidationService.getPhoneCode(token, phone);
    }

    /**
     * 接码平台登录
     *
     * @return
     */
    @GetMapping("/tokenLogin")
    public GlobalResult tokenLogin() {
        return codeValidationService.tokenLogin();
    }

    /**
     * 接码平台获取项目号码
     *
     * @return
     */
    @GetMapping("/tokenPhone")
    public GlobalResult tokenPhone(String code){
        return codeValidationService.tokenPhone(code);
    };

    /**
     * 接码平台注销号码
     */
    @GetMapping("/resume")
    public GlobalResult resume(String token, String phoneNo) {
        return codeValidationService.resume(token, phoneNo);
    }
}
