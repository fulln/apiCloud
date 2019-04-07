package com.fulln.me.web.config.shiro;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.fulln.me.api.common.utils.FileUtil;
import com.fulln.me.api.model.system.SysPermission;
import com.fulln.me.api.service.system.ISysPermissionService;
import com.fulln.me.web.config.constant.NonBlockingUrlConfig;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.*;

/**
 * @Author: fulln
 * @Description shiroconfig类
 * @Date: 2018/9/27 0027-9:09
 */

@Configuration
public class ShiroConfig {

    @Resource
    private ISysPermissionService permissionService;

    @Autowired
    private NonBlockingUrlConfig urlConfig;

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     *
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


    /**
     * shiro注解
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor myAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager());
        return advisor;
    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * <p>
     * Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();


        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl(urlConfig.getLoginUrl());
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl(urlConfig.getSuccessUrl());
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl(urlConfig.getUnauthorizedUrl());

        //修改自定义退出地址
        Map<String, Filter> filters = new LinkedHashMap<>();
        DefaultLogoutFilter logoutFilter = new DefaultLogoutFilter();
        filters.put("logout", logoutFilter);

        //自定义角色url认证
        UrlPathMatchingFilter urlPathMatchingFilter = new UrlPathMatchingFilter();
        filters.put("authc", urlPathMatchingFilter);

        //token 设置自定义拦截
        //shiro 跨域请求的时候设置option 不拦截
        MyAuthenticationFilter myAuthenticationFilter = new MyAuthenticationFilter();
//        myAuthenticationFilter.setDefaultHead(headConfig);
//        myAuthenticationFilter.setRedisUtil(redisUtil);
        filters.put("authc", myAuthenticationFilter);


        shiroFilterFactoryBean.setFilters(filters);


        //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        List<String> nonBlockingUrl = urlConfig.getNonBlockingUrl();

        nonBlockingUrl.forEach(url ->
                filterChainDefinitionMap.put(url, "anon")
        );

        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put(urlConfig.getLogoutUrl(), "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        //自定义加载权限资源关系
        List<SysPermission> resourcesList = permissionService.queryAll();
        for (SysPermission resources : resourcesList) {
            if (!StringUtils.isEmpty(resources.getResourceUrl())) {
                String permission = "perms[" + resources.getResourceUrl() + "]";
                filterChainDefinitionMap.put(resources.getResourceUrl(), permission);
            }
        }
        filterChainDefinitionMap.put("/**", "authc");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理
        securityManager.setSessionManager(sessionManager());
        //自定义rememberme的管理
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * cookie管理对象;记住我功能
     *
     * @return
     */
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;

    }

    /**
     * cookie对象;
     *
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }


    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(authCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     *
     * @return
     */
    @Bean
    public AuthCredential authCredentialsMatcher() {
        return new AuthCredential();
    }


    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param SecurityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        String platform = FileUtil.getProperty("spring.profiles.active");
        Properties props = FileUtil.getProps("application-" + platform + ".yml");

        //propertise的配置
        redisManager.setHost(props.getProperty("spring.redis.host"));
        redisManager.setPort(Integer.parseInt(Objects.requireNonNull(props.getProperty("spring.redis.port"))));
        // 配置缓存过期时间
        redisManager.setExpire(18000);
        redisManager.setTimeout(Integer.parseInt(Objects.requireNonNull(FileUtil.getProperty("spring.redis.timeout"))));
        String pass = props.getProperty("spring.redis.password");


        if (!StringUtils.isEmpty(pass)) {
            redisManager.setPassword(pass);
        }
        return redisManager;
    }


    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        /*会话id生成*/
        redisSessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        return redisSessionDAO;
    }

//    @Bean
//    public SimpleCookie sharesession(){
//        SimpleCookie simpleCookie=  new SimpleCookie();
//        simpleCookie.setName("SHAREJSESSIONID");
//        simpleCookie.setPath("/");
//        return simpleCookie;
//    }

    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        SimpleWebSessionManager sessionManager = new SimpleWebSessionManager();
        sessionManager.setDeleteInvalidSessions(true);
        //设置session过期    不过期可以设置-1000
        sessionManager.setGlobalSessionTimeout(86400000);
        sessionManager.setCacheManager(cacheManager());
        sessionManager.setSessionValidationInterval(3000000);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

}
