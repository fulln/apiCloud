package com.fulln.me.web.config.aop;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.enums.GlobalEnums;
import com.fulln.me.api.common.enums.view.ViewActiveEnmus;
import com.fulln.me.api.common.utils.DateUtil;
import com.fulln.me.api.common.utils.GsonUtil;
import com.fulln.me.api.common.utils.RequestIpUtil;
import com.fulln.me.api.model.log.LogOperationInfo;
import com.fulln.me.api.model.system.SysUserBasic;
import com.fulln.me.web.config.annotation.userMessage;
import com.fulln.me.web.config.base.method.BaseController;
import com.fulln.me.web.service.basic.IThreadStartService;
import com.fulln.me.web.service.log.ILogOperationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;


/**
 * @Author: fulln
 * @Description: aop处理系统操作记录
 * @Date: Created in 2018/5/2 0002
 */
@Aspect
@Component
@Slf4j
public class AspectScan extends BaseController {

    @Resource
    private ILogOperationService operationService;

    @Resource
    private IThreadStartService startService;
    private Parameter parameter;


    @Pointcut("@within(org.springframework.stereotype.Service) && execution(* com.fulln.me.web.service.*.*(..))")
    public void servicehandle() {

    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) && execution(* com.fulln.me.web.controller.*.*.*(..))")
    public void controllerhandle() {

    }

    @Pointcut("@within(org.springframework.stereotype.Controller) && execution(* com.fulln.me.web.controller.guideview.*.*(..))")
    public void viewcontrollerhandle() {

    }

    @Pointcut("@annotation(com.fulln.me.web.config.annotation.userMessage)")
    public void getUserInfo() {

    }

    /**
     * 日志访问记录  用户操作据记录表
     *
     * @param joinPoint
     */
    @After("controllerhandle()")
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
        if (getUser() != null) {
            operationInfo.setOperationUser(getUser().getUserName());
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
    @After("viewcontrollerhandle()")
    public void viewController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("active", ViewActiveEnmus.valueOf(methodName.toUpperCase()).ordinal());
    }


    @Around("servicehandle()")
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

    //AfterThrowing异常发生后能执行的方法  但是他并不能阻止异常的抛出或者catch  系统异常记录表
    /*@AfterThrowing(pointcut = "gethandle()", throwing = "e")
    public GlobalResult AfterThrows(JoinPoint jp, Throwable e) {
        log.error("ExceptionHandler : ", e);
        if (e instanceof IllegalArgumentException) {
            return getfaultResult(e.getMessage());
        } else if (e instanceof NullPointerException) {
            return getfaultResult(e.getMessage());
        } else if (e instanceof BadSqlGrammarException) {
            return getfaultResult("内部错误！请联系管理员！");
        } else {
            return getfaultResult("系统异常！");
        }
    }*/

    /**
     * 手动添加用户信息到公共实体类中
     * @param joinPoint
     */
    @Before("getUserInfo()")
    public void insertUserInfo(JoinPoint joinPoint) {

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            userMessage annotation = method.getAnnotation(userMessage.class);
            try {
                Method userMethod = method.getDeclaringClass().getMethod(annotation.methodName());
                SysUserBasic userBasic = (SysUserBasic) userMethod.invoke(method.getDeclaringClass());
                Parameter[] parameters = method.getParameters();
                Arrays.stream(parameters)
                        .filter(parameter ->parameter.getName().equals(annotation.value()))
                        .forEach(param -> {
                            try {
                                Method setUserBasic = param.getClass().getSuperclass().getDeclaredMethod("setUserBasic", SysUserBasic.class);
                                setUserBasic.invoke(param.getClass().getSuperclass(),userBasic);
                            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


}
