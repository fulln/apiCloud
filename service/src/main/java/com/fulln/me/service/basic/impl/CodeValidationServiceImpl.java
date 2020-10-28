package com.fulln.me.service.basic.impl;


import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.common.enums.GlobalEnums;
import me.fulln.base.common.enums.QueryHttpsEnums;
import me.fulln.base.common.utils.httpUtil;
import com.fulln.me.service.basic.CodeValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: api
 * @description: 接码平台业务层实现
 * @author: fulln
 * @create: 2018-09-21 10:59
 * @Version： 0.0.1
 **/
@Service
@Slf4j
public class CodeValidationServiceImpl implements CodeValidationService {

    @Value("${token.platform.user}")
    private String name;

    @Value("${token.platform.pass}")
    private String psw;

    @Override
    public GlobalResult getPhoneCode(String token, String phone) {
        try {
            Map<String, String> map = new HashMap<>(2);
            map.put("token", token);
            map.put("hm", phone);
            String code = httpUtil.ClientGetRequest(QueryHttpsEnums.TOKEN_PLATFORM_TOKEN.geturls());
            if (code == null) {

                return  GlobalEnums.TIME_OUT_ERROR.results();
            } else if (code.length() < 4) {
                return  GlobalEnums.PLATFORM_ERROR.results();
            }
            return  GlobalEnums.QUERY_SUCCESS.results(code);
        } catch (Exception e) {
            log.error("接码平台登录" + e);
            return  GlobalEnums.QUERY_FAIL.results();
        }
    }


    @Override
    public GlobalResult tokenLogin() {
        try {
            Map<String, String> map = new HashMap<>(2);
            map.put("name", name);
            map.put("psw", psw);

            String code = httpUtil.ClientGetRequest(QueryHttpsEnums.TOKEN_PLATFORM_LOGIN.geturls(map));

            if (code == null) {
                return  GlobalEnums.QUERY_EMPTY.results();
            }
            return  GlobalEnums.QUERY_SUCCESS.results(code);
        } catch (Exception e) {
            log.error("接码平台登录" + e);
            return  GlobalEnums.QUERY_FAIL.results();
        }
    }

    @Override
    public GlobalResult tokenPhone(String code) {
        try {
            Map<String, String> map = new HashMap<>(2);
            map.put("token", code);
            String phone = httpUtil.ClientGetRequest(QueryHttpsEnums.TOKEN_PLATFORM_PHONE.geturls(map));
            return  GlobalEnums.QUERY_SUCCESS.results(phone);
        } catch (Exception e) {
            return  GlobalEnums.QUERY_FAIL.results();
        }
    }

    @Override
    public GlobalResult resume(String token, String phoneNo) {
        Map<String, String> map = new HashMap<>(2);
        map.put("token", token);
        map.put("hm ", phoneNo);
        httpUtil.ClientGetRequest(QueryHttpsEnums.TOKEN_PLATFORM_RELEASE.geturls(map));
        return  GlobalEnums.QUERY_SUCCESS.results();
    }
}
