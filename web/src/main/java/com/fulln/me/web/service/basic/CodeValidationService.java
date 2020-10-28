package com.fulln.me.web.service.basic;


import me.fulln.base.common.entity.GlobalResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: api
 * @description: 接码平台业务层接口
 * @author: fulln
 * @create: 2018-09-21 10:50
 * @Version： 0.0.1
 **/
@FeignClient(value = "${feign.url}",path = "${feign.path}")
public interface CodeValidationService {


    /**
     * 接码平台获取token
     *
     * @return
     */
    @GetMapping("/CodeValidate/getPhoneCode")
    GlobalResult getPhoneCode(@RequestParam String token, @RequestParam String phone);

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
    GlobalResult tokenPhone(@RequestParam("code") String code);

    /**
     * 接码平台注销号码
     */
    @GetMapping("/CodeValidate/resume")
    GlobalResult resume(@RequestParam("token") String token, @RequestParam("phoneNo") String phoneNo);
}
