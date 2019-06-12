package com.fulln.me.web.config.base.method;


import com.fulln.me.api.common.utils.AesUtil;
import com.fulln.me.api.model.user.SysUserBasic;
import com.fulln.me.web.config.intecepter.MyHandlerInterceptor;
import com.fulln.me.web.config.redis.RedisUtil;
import com.fulln.me.web.config.shiro.SimpleWebSessionManager;
import com.fulln.me.web.service.system.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @AUthor: fulln
 * @Description: 公共的controller
 * @Date : Created in  20:30  2018/9/1.
 */
public class BaseController {


    @Resource
    private MyHandlerInterceptor handlerInterceptor;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysUserService userService;

    /**
     * 分开进行获取  获取用户信息.
     */
    protected SysUserBasic getUser() {
        SysUserBasic currentUser = new SysUserBasic();
        Subject sessionCurrentUser = SecurityUtils.getSubject();

        if (null != sessionCurrentUser) {
            Session session = sessionCurrentUser.getSession();
            if (null != session) {
                currentUser = (SysUserBasic) session.getAttribute("currentUser");
            }
        } else {

            HttpServletRequest sessionRequest = handlerInterceptor.getThisRequest();

            String user = sessionRequest.getHeader("userName");
            String token = sessionRequest.getHeader(SimpleWebSessionManager.ACCESSTOKEN);

            if (user != null && token != null) {
                currentUser.setCurrentToken(token);
                currentUser.setUserName(user);

                if (!redisUtil.hasKey(token)) {
                    currentUser = userService.selectByUsername(user);
                } else {
                    Map<Object, Object> tokenMap = redisUtil.hmget(SimpleWebSessionManager.DEFAULT_TOKEN_KEY + AesUtil.AESDecode(token));
                    if (tokenMap != null) {
                        Session session = (Session) redisUtil.get(SimpleWebSessionManager.SHIRO_REDIS_SESSION + tokenMap.get("sessionId"));
                        currentUser = (SysUserBasic) session.getAttribute("currentUser");
                        currentUser.setCurrentToken(token);
                    }
                }
            }
        }
        return currentUser;
    }

}
