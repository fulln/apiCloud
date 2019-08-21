package com.fulln.me.web.config.resolver;

import com.fulln.me.api.common.annotation.CurrentUser;
import com.fulln.me.api.common.utils.AesUtil;
import com.fulln.me.api.model.user.SysUserBasic;
import com.fulln.me.web.config.redis.RedisUtil;
import com.fulln.me.web.config.shiro.SimpleWebSessionManager;
import com.fulln.me.web.service.system.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;

/**
 * @author fulln
 * @apiNote 参数自动注入
 * @date  Created in  00:21  2019-08-22.
 */
@Component
public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {


	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private ISysUserService userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentUser.class);
	}
 
	@Override
	public Object resolveArgument(MethodParameter parameter,
								  ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
								  WebDataBinderFactory binderFactory) throws Exception {
		CurrentUser currentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
		SysUserBasic currentUser = new SysUserBasic();
		Subject sessionCurrentUser = SecurityUtils.getSubject();

		if (null != sessionCurrentUser) {
			Session session = sessionCurrentUser.getSession();
			if (null != session) {
				currentUser = (SysUserBasic) session.getAttribute("currentUser");
			}
		} else {
			String user = webRequest.getHeader("userName");
			String token = webRequest.getHeader(SimpleWebSessionManager.ACCESSTOKEN);

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
