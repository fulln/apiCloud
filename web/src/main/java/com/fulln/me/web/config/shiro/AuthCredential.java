package com.fulln.me.web.config.shiro;


import com.fulln.me.api.common.utils.AesUtil;
import com.fulln.me.api.common.utils.DateUtil;
import com.fulln.me.api.common.utils.MD5util;
import com.fulln.me.api.model.system.SysUserBasic;
import com.fulln.me.api.service.system.ISysUserService;
import com.fulln.me.web.config.constant.DefaultHeadConfig;
import com.fulln.me.web.config.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AuthCredential extends SimpleCredentialsMatcher {
    /**
     * 将用户的明文进行加密
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token,
                                      AuthenticationInfo info) {
        //获取用户名和密码
        UsernamePasswordToken loginToken = (UsernamePasswordToken) token;
        String password = String.valueOf(loginToken.getPassword());
        //获取用户信息
        SysUserBasic sysUser = getUser();
        //通过加密算法 获取密文
        String md5Password = MD5util.getMd5Hash(password, sysUser.getUserSalt());
        //将密码设置回token中
        loginToken.setPassword(md5Password.toCharArray());

        boolean result = super.doCredentialsMatch(loginToken, info);
        if (!result) {
            Session session = SecurityUtils.getSubject().getSession();

            int loadNum = session.getAttribute("loadNum") == null ? sysUser.getLoginFailCounts() + 1 : getMaxFail(sysUser.getLoginFailCounts(), (Integer) session.getAttribute("loadNum")) + 1;

            sysUserService.updateLoginFail(sysUser.getUserName(), loadNum);

            if (loadNum > 3) {
                throw new ExcessiveAttemptsException();
            }

        } else {

            String redisKey = SimpleWebSessionManager.DEFAULT_TOKEN_KEY + sysUser.getUserId() + DateUtil.getNowDateStamp();


//            //删除上一个账号登陆产生的session
            if (redisUtil.hasKey(redisKey)) {
                try {
                    Map<Object, Object> defaultMap = redisUtil.hmget(redisKey);
                    redisUtil.del(SimpleWebSessionManager.SHIRO_REDIS_SESSION + defaultMap.get("sessionId"));
                } catch (Exception e) {
                    log.error("删除上一个session失败!");
                }
            }

            Session session = SecurityUtils.getSubject().getSession();

            Map<String, Object> map = new HashMap<>(3);
            map.put("time", DateUtil.getNowTimeStamp());
            map.put("sessionId", session.getId());
            map.put("userName", sysUser.getUserName());

            //不需要存时间  多处设置了自动清除
            redisUtil.hmset(redisKey, map);

            //如果是admin登陆  需要重新产生一个最高权限token
            if (headConfig.getUserName().equals(sysUser.getUserName())) {
                redisKey = SimpleWebSessionManager.DEFAULT_TOKEN_KEY + AesUtil.AESDecode(headConfig.getToken());
            }
            redisUtil.hmset(redisKey, map);

        }
        return result;
    }

    private int getMaxFail(Integer loginFail, Integer loadNum) {
        if (loginFail > loadNum) {
            return loginFail;
        } else {
            return loadNum;
        }
    }

    private SysUserBasic getUser() {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                return (SysUserBasic) session.getAttribute("currentUser");
            }
        }
        return null;
    }

    private ISysUserService sysUserService;
    private RedisUtil redisUtil;
    private DefaultHeadConfig headConfig;



    public ISysUserService getSysUserService() {
        return sysUserService;
    }


    @Autowired
    public void setSysUserService(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    public RedisUtil getRedisUtil() {
        return redisUtil;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public DefaultHeadConfig getHeadConfig() {
        return headConfig;
    }

    @Autowired
    public void setHeadConfig(DefaultHeadConfig headConfig) {
        this.headConfig = headConfig;
    }
}
