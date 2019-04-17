package com.fulln.me.web.service.basic;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.service.basic.CodeValidationService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: api
 * @description: 接码平台业务层接口
 * @author: fulln
 * @create: 2018-09-21 10:50
 * @Version： 0.0.1
 **/
@FeignClient("${feign.url}")
public interface WebCodeValidationService  extends CodeValidationService {


    /**
     * 接码平台获取token
     *
     * @return
     */
    @GetMapping("/CodeValidate/getPhoneCode")
    GlobalResult getPhoneCode(String token, String phone);

    /**
     * 接码平台登录
     *
     * @return
     */
    @GetMapping("/CodeValidate/tokenLogin")
    GlobalResult tokenLogin();

    /**
     * 接码平台获取项目号码
     *
     * @return
     */
    @GetMapping("/CodeValidate/tokenPhone")
    GlobalResult tokenPhone(String code);

    /**
     * 接码平台注销号码
     */
    @GetMapping("/CodeValidate/resume")
    GlobalResult resume(String token, String phoneNo);
}
