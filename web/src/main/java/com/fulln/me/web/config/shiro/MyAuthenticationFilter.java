package com.fulln.me.web.config.shiro;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.enums.GlobalEnums;
import com.fulln.me.api.common.utils.AesUtil;
import com.fulln.me.api.common.utils.DateUtil;
import com.fulln.me.api.common.utils.GsonUtil;
import com.fulln.me.web.config.constant.DefaultHeadConfig;
import com.fulln.me.web.config.redis.RedisUtil;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @program: MyAuthenticationFilter
 * @description: shrio的拦截器
 * @author: fulln
 * @Date 11:00 2019/1/15 0015
 * @Version： 0.0.1
 **/
public class MyAuthenticationFilter extends FormAuthenticationFilter {

    private RedisUtil redisUtil;
    private DefaultHeadConfig defaultHead;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //shiro的过滤器
        if (request instanceof HttpServletRequest) {
            if (RequestMethod.OPTIONS.name().equals(((HttpServletRequest) request).getMethod())) {
                return true;
            }

            String token = ((HttpServletRequest) request).getHeader(SimpleWebSessionManager.ACCESSTOKEN);
            String userName = ((HttpServletRequest) request).getHeader("username");
            //当前访问的时候是否带有token和姓名
            if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(userName)) {
                //是不是带着万能token进行的访问
                if (defaultHead.getUserName().equals(userName) && defaultHead.getToken().equals(token)) {
                    return true;
                }else{
                    //redis中是否存在当前的token
                    if(!redisUtil.hasKey(SimpleWebSessionManager.DEFAULT_TOKEN_KEY + AesUtil.AESDecode(token))){
                        return false;
                    }
                    Map<Object, Object> tokenMap = redisUtil.hmget(SimpleWebSessionManager.DEFAULT_TOKEN_KEY + AesUtil.AESDecode(token));
                    if (tokenMap != null && tokenMap.size() != 0) {
                        String name = (String) tokenMap.get("userName");
                        Long time = Long.valueOf((String) tokenMap.get("time"));
                        if (name.equals(userName)) {
                            Long timeTemp = DateUtil.getNowDateStamp();
                            if (timeTemp - time <= defaultHead.getSessionOutTime()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        boolean re = super.onAccessDenied(request, response);
        if (!re) {
            response.setContentType("text/json");
            response.setCharacterEncoding("UTF8");
            GlobalResult result = GlobalEnums.USER_HAS_NO_PERMISSION.results();
            try {
                response.getWriter().write(GsonUtil.gsonString(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return re;
    }

    public RedisUtil getRedisUtil() {
        return redisUtil;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public DefaultHeadConfig getDefaultHead() {
        return defaultHead;
    }

    @Autowired
    public void setDefaultHead(DefaultHeadConfig defaultHead) {
        this.defaultHead = defaultHead;
    }
}
