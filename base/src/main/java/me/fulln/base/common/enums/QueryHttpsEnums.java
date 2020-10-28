package me.fulln.base.common.enums;


import me.fulln.base.common.enums.config.EnumsConfig;

import java.util.HashMap;
import java.util.Map;

public enum QueryHttpsEnums implements EnumsConfig {

    TOKEN_PLATFORM_LOGIN(1001, "接码平台登录获取token", "http://www.js-yzm.com:9180/service.asmx/UserLoginStr", null),
    TOKEN_PLATFORM_PHONE(1002, "接码平台接收手机号", "http://www.js-yzm.com:9180/service.asmx/GetHM2Str", new HashMap<String, String>() {{
        put("xmid", "253");
        put("sl", "1");
        put("lx ", "2");
        put("ks", "0");
    }}),
    TOKEN_PLATFORM_TOKEN(1003, "接码平台接收手机验证码", "http://www.js-yzm.com:9180/service.asmx/GetYzm2Str", new HashMap<String, String>() {{
        put("xmid", "253");
        put("sf", "1");
    }}),
    TOKEN_PLATFORM_RELEASE(1004, "接码平台释放手机号", "http://www.js-yzm.com:9180/service.asmx/sfHmStr", null),
    ELEME_PLATFORM_LOGIN_TOKEN(1005, "饿了么平台登录请求验证码", "https://h5.ele.me/restapi/eus/login/mobile_send_code ", null),
    ELEME_PLATFORM_LOGIN(1006, "饿了么平台登录", "https://h5.ele.me/restapi/eus/login/login_by_mobile", null),
    ELEME_PLATFORM_PHONE(1007, "饿了么平台绑定手机号", "https://h5.ele.me/restapi/marketing/hongbao/weixin/${code}/change", null),
    ELEME_PLATFORM_HONGBAO(1008, "饿了么平台领取红包", "https://h5.ele.me/restapi/marketing/promotion/weixin/${code}", null);


    public int code;
    public String message;
    public Object datas;
    public Map<String, String> params;

    QueryHttpsEnums(int code, String message, Object datas, Map<String, String> params) {
        this.code = code;
        this.params = params;
        this.datas = datas;
    }

    public String geturls(Map<String, String> params) {
        String urls = geturls();
        if (datas + "" != urls) {
            StringBuffer sb = new StringBuffer();
            sb.append(urls);
            if (params != null) {
                params.forEach(
                        (k, v) -> sb.append("&" + k + "=" + v)
                );
            }
            return sb.toString();
        } else {
            return this.datas + "";
        }

    }

    public String geturls() {
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            params.forEach(
                    (k, v) -> sb.append("&" + k + "=" + v)
            );
            return this.datas + sb.toString().replaceFirst("&", "?");
        } else {
            return this.datas + "";
        }

    }

    public String getRestUrls() {
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            params.forEach(
                    (k, v) -> sb.append(v)
            );
            return this.datas.toString().replace("${code}", sb);

        } else {
            return null;
        }
    }


    public String getRestUrls(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            params.forEach(
                    (k, v) -> sb.append(v)
            );
            return this.datas.toString().replace("${code}", sb);
        } else {
            return null;
        }

    }
}
