package com.fulln.me.service.basic;


import com.fulln.me.api.common.entity.GlobalResult;

/**
 * @program: api
 * @description: 接码平台业务层接口
 * @author: fulln
 * @create: 2018-09-21 10:50
 * @Version： 0.0.1
 **/
public interface CodeValidationService {


    /**
     * 接码平台获取token
     *
     * @return
     */
    GlobalResult getPhoneCode(String token, String phone);

    /**
     * 接码平台登录
     *
     * @return
     */
    GlobalResult tokenLogin();

    /**
     * 接码平台获取项目号码
     *
     * @return
     */
    GlobalResult tokenPhone(String code);

    /**
     * 接码平台注销号码
     */
    GlobalResult resume(String token, String phoneNo);
}
