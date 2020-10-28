package com.fulln.me.web.config.aop;


import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.common.enums.GlobalEnums;
import me.fulln.base.common.enums.view.ViewActiveEnmus;
import me.fulln.base.common.utils.DateUtil;
import me.fulln.base.common.utils.GsonUtil;
import me.fulln.base.common.utils.RequestIpUtil;
import me.fulln.base.model.log.LogOperationInfo;
import me.fulln.base.model.user.SysUserBasic;
import com.fulln.me.web.service.basic.IThreadStartService;
import com.fulln.me.web.service.log.ILogOperationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * @Author: fulln
 * @Description: aop处理系统操作记录
 * @Date: Created in 2018/5/2 0002
 */
@Aspect
@Component
@Slf4j
public class AspectScan{

    @Resource
    private ILogOperationService operationService;

    @Resource
    private IThreadStartService startService;
    private Parameter parameter;


    @Pointcut("@within(org.springframework.stereotype.Service) && execution(* com.fulln.me.web.service.*.*(..))")
    public void serviceHandle() {

    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) && execution(* com.fulln.me.web.controller.*.*.*(..))")
    public void controllerHandle() {

    }

    @Pointcut("@within(org.springframework.stereotype.Controller) && execution(* com.fulln.me.web.controller.guideview.*.*(..)) ")
    public void viewcontrollerHandle() {

    }


    /**
     * 日志访问记录  用户操作据记录表
     *
     * @param joinPoint
     */
    @After("controllerHandle()")
    public void after(JoinPoint joinPoint) throws NoSuchMethodException {

        String methodName = joinPoint.getSignature().getName();
        joinPoint.getArgs();

        if (methodName.contains("find") || methodName.contains("select")) {
            return;
        }

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求的request
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        SysUserBasic currentUser = (SysUserBasic)request.getAttribute("currentUser");
        LogOperationInfo operationInfo = new LogOperationInfo();

        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        String headMaps = GsonUtil.gsonString(map);

        //封装实体
        operationInfo.setRequestType(request.getMethod());
        operationInfo.setOperationUrl(request.getRequestURL().toString());
        operationInfo.setOperationDate(DateUtil.getNowTimeStamp());
        operationInfo.setRequestHead(headMaps);
        if (currentUser != null) {
            operationInfo.setOperationUser(currentUser.getUserName());
        } else if (request.getHeader("userName") != null) {
            operationInfo.setOperationUser(request.getHeader("userName"));
        } else {
            operationInfo.setOperationUser("api");
        }
        operationInfo.setUserIp(RequestIpUtil.getIpAddress(request));
        operationInfo.setOperationParams(request.getQueryString());
        operationInfo.setOperationMethod(methodName);
        operationService.save(operationInfo);

    }


    /**
     * 动态跳转active
     *
     * @param joinPoint
     */
    @After("viewcontrollerHandle()")
    public void viewController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("active", ViewActiveEnmus.valueOf(methodName.toUpperCase()).ordinal());
    }


    @Around("serviceHandle()")
    public GlobalResult aroundService(ProceedingJoinPoint pJoinPoint) {
        return resultHandle(pJoinPoint);
    }

    /**
     * 可以选择自定义service异常
     *
     * @param pJoinPoint
     * @return
     */
    private GlobalResult resultHandle(ProceedingJoinPoint pJoinPoint) {

        GlobalResult result;
        try {
            result = (GlobalResult) pJoinPoint.proceed();
        } catch (Exception e) {
            log.error("[aop]未捕获的异常", e);
            result = GlobalEnums.SYS_ERROR.results();
        } catch (Throwable throwable) {
            log.error("[aop]异常处理时发生异常", throwable);
            result = GlobalEnums.SYS_ERROR.results();
        }
        return result;
    }


}
