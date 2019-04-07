package com.fulln.me.web.config.shiro;


import com.fulln.me.api.common.utils.AesUtil;
import com.fulln.me.web.config.constant.DefaultHeadConfig;
import com.fulln.me.web.config.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 改sessionID认证
 * 为token认证
 * 设置最高权限token
 */
@Slf4j
public class SimpleWebSessionManager extends DefaultWebSessionManager implements WebSessionManager {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DefaultHeadConfig headConfig;

    private CacheManager cacheManager;

    public static final String DEFAULT_TOKEN_KEY = "token_";
    public static final String ACCESSTOKEN = "Access-Token";
    public static final String SHIRO_REDIS_SESSION = "shiro_redis_session:";

    private String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(ACCESSTOKEN);
        return accessToken != null ? accessToken : "";
    }



//    @Override
//    public Serializable getSessionId(SessionKey sessionKey) {
//        Serializable id = super.getSessionId(sessionKey);
//        if (null == id && WebUtils.isWeb(sessionKey)) {
//            HttpServletRequest request = WebUtils.getHttpRequest(sessionKey);
//            String accessToken = getAccessToken(request);
//            if (!StringUtils.isEmpty(accessToken)) {
//                Map<Object,Object> map =redisUtil.hmget(DEFAULT_TOKEN_KEY + accessToken);
//                id = (String) map.get("sessionId");
//            }
//        }
//        return id;
//    }

    @Override
    protected void onExpiration(Session s, ExpiredSessionException ese, SessionKey key) {
        super.onExpiration(s, ese, key);
        onInvalidation(key);
    }

    @Override
    protected void onInvalidation(Session s, InvalidSessionException ise, SessionKey key) {
        super.onInvalidation(s, ise, key);
        onInvalidation(key);
    }

    private void onInvalidation(SessionKey key) {
        ServletRequest request = WebUtils.getRequest(key);
        if (request != null) {
            request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID);
        }
        if (WebUtils.isHttp(key)) {
            log.debug("Referenced session was invalid.  Removing session ID Access-Token.");
            HttpServletRequest httpRequest = WebUtils.getHttpRequest(key);
            String accessToken = getAccessToken(httpRequest);
            if (!StringUtils.isEmpty(accessToken)) {
                if (!headConfig.getToken().equals(accessToken)) {
                    Map<Object, Object> map = redisUtil.hmget(DEFAULT_TOKEN_KEY + AesUtil.AESDecode(accessToken));
                    redisUtil.del(SHIRO_REDIS_SESSION + map.get("sessionId"));
                    redisUtil.del(DEFAULT_TOKEN_KEY + AesUtil.AESDecode(accessToken));
                }
            }
        } else {
            log.debug("SessionKey argument is not HTTP compatible or does not have an HTTP request/response " +
                    "pair. Session ID Access-Token will not be removed due to invalidated session.");
        }
    }

    @Override
    protected void onStop(Session session, SessionKey key) {
        super.onStop(session, key);
        onInvalidation(key);
    }


    @Override
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        super.setCacheManager(cacheManager);
    }


    public SimpleWebSessionManager() {
        super();
    }


    @Override
    public void validateSessions() {
        if (log.isDebugEnabled())
            log.debug("Validating all active sessions...");

        int invalidCount = 0;
        Collection<?> activeSessions = getActiveSessions();
        if (activeSessions != null && !activeSessions.isEmpty()) {
            for (Iterator<?> i$ = activeSessions.iterator(); i$.hasNext(); ) {
                Session session = (Session) i$.next();
                try {
                    SessionKey key = new DefaultSessionKey(session.getId());
                    validate(session, key);
                } catch (InvalidSessionException e) {
                    SessionKey key = new DefaultSessionKey(session.getId());
                    onInvalidation(key);
                    if (log.isDebugEnabled()) {
                        boolean expired = e instanceof ExpiredSessionException;
                        String msg = "Invalidated session with id [" +
                                session.getId() + "]" +
                                (expired ? " (expired)" : " (stopped)");
                        log.debug(msg);
                    }
                    invalidCount++;
                }
            }

        }
        if (log.isInfoEnabled()) {
            String msg = "Finished session validation.";
            if (invalidCount > 0) {
                msg = (new StringBuilder()).append(msg).append("  [").append(
                        invalidCount).append("] sessions were stopped.")
                        .toString();
            } else {
                msg = (new StringBuilder()).append(msg).append(
                        "  No sessions were stopped.").toString();
            }
            log.debug(msg);
        }
    }
}