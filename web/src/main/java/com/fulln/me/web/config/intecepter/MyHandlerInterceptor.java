package com.fulln.me.web.config.intecepter;


import me.fulln.base.common.enums.GlobalEnums;
import me.fulln.base.common.utils.AesUtil;
import me.fulln.base.common.utils.DateUtil;
import me.fulln.base.common.utils.GsonUtil;
import com.fulln.me.web.config.constant.DefaultHeadConfig;
import com.fulln.me.web.config.constant.NonBlockingUrlConfig;
import com.fulln.me.web.config.redis.RedisUtil;
import com.fulln.me.web.config.shiro.SimpleWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @AUthor: fulln
 * @Description: 拦截器
 * @Date : Created in  20:34  2018/9/1.
 */
@Component
public class MyHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private DefaultHeadConfig defaultHead;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private NonBlockingUrlConfig nonBlockingUrlConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Accept,token,name,X-Requested-With");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if (RequestMethod.OPTIONS.name().equals(request.getMethod())) {
            response.setStatus(200);
            return false;
        }

        String url = request.getRequestURI();

        boolean flag = false;
        if (!matchStringByIndexOf(nonBlockingUrlConfig.getNonBlockingUrl(), url) && !nonBlockingUrlConfig.getLoginUrl().contains(url)
                && !matchStringByIndexOf(url, nonBlockingUrlConfig.getSuccessUrl()) && !nonBlockingUrlConfig.getLogoutUrl().contains(url)
                && !nonBlockingUrlConfig.getUnauthorizedUrl().contains(url)
        ) {
            String token = request.getHeader("token");
            String userName = request.getHeader("userName");
            if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(userName)) {
                if (!token.equals(defaultHead.getToken())) {
                    Map<Object, Object> tokenMap = redisUtil.hmget(SimpleWebSessionManager.DEFAULT_TOKEN_KEY + AesUtil.AESDecode(token));
                    if (tokenMap != null) {
                        String name = (String) tokenMap.get("userName");
                        Long time = Long.valueOf((String) tokenMap.get("time"));
                        if (!name.equals(userName)) {
                            flag = true;
                        }
                        Long timeTemp = DateUtil.getNowDateStamp();
                        if (timeTemp - time > defaultHead.getSessionOutTime()) {
                            redisUtil.del(SimpleWebSessionManager.DEFAULT_TOKEN_KEY + AesUtil.AESDecode(token));
                            redisUtil.del(SimpleWebSessionManager.SHIRO_REDIS_SESSION + tokenMap.get("sessionId"));
                            flag = true;
                        }
                    } else {
                        flag = true;
                    }
                }
            } else {
                if(!redisUtil.hasKey(SimpleWebSessionManager.SHIRO_REDIS_SESSION+request.getSession().getId())){
                    flag = true;
                }
            }
        }
        if (flag) {
            //交易码过期
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            try (PrintWriter out = response.getWriter()) {
                out.append(GsonUtil.gsonString(GlobalEnums.TIME_OUT_ERROR.results()));
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            return true;
        }

    }


    /**
     * url匹配
     */

    private boolean matchStringByIndexOf(String parent, String child) {
        int count = 0;
        int index = 0;
        while ((index = parent.indexOf(child, index)) != -1) {
            index = index + child.length();
            count++;
        }
        return count > 0;
    }

    /**
     * url 多行验证
     */
    private boolean matchStringByIndexOf(List<String> parent, String child) {
        AtomicInteger count = new AtomicInteger();

        parent.forEach(childs -> {
            if (!childs.contains("*")) {
                if (childs.contains(child)) {
                    count.incrementAndGet();
                }
            } else {
                childs = childs.replace("*", "");
                if (childs.contains(child)) {
                    count.incrementAndGet();
                }
            }
        });
        return count.get() > 0;
    }
}
