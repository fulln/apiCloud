package com.fulln.me.web.controller.guideview;


import com.fulln.me.api.common.enums.RegixEnums;
import com.fulln.me.api.common.utils.DateUtil;
import com.fulln.me.api.model.system.SysPermission;
import com.fulln.me.api.model.system.SysRolePermission;
import com.fulln.me.web.service.system.ISysPermissionService;
import com.fulln.me.web.service.system.ISysRolePermissionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @AUthor: fulln
 * @Description: 视图跳转层
 * @Date : Created in  17:50  2018/9/16.
 */
@Slf4j
@Controller
public class ViewController {


    @Resource
    private ISysRolePermissionService sysRolePermissionService;

    @Resource
    private ISysPermissionService sysPermissionService;


    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @RequestMapping("/view")
    public String play() {
        return "play/play";
    }

    @RequestMapping("/single")
    public String single() {
        return "play/singlePlay";
    }

    @RequestMapping("/editor")
    public String editor() {
        return "article/editor";
    }

    @RequestMapping("/websocket")
    public String socket() {
        return "websocket/webSocket";
    }




    /**
     *
     * 自动添加url到权限表最高权限
     */
//    @PostConstruct
//    @RequestMapping
    public void addParams() {


        Map<RequestMappingInfo, HandlerMethod> requestMap = requestMappingHandlerMapping.getHandlerMethods();

        requestMap.entrySet().stream()
                .collect(Collectors.toMap(
                        keys -> keys.getKey().getPatternsCondition().toString(),
                        keys -> {
                            String paths = keys.getValue().getMethod().getDeclaringClass().getName();
                            if (paths.matches(RegixEnums.CLASSPATH_PATTERN.getDatas())) {
                                if (keys.getValue().hasMethodAnnotation(ApiOperation.class)) {
                                    return Objects.requireNonNull(keys.getValue().getMethodAnnotation(ApiOperation.class)).value();
                                }
                            }
                            return "0";
                        },
                        (oldValue, newValue) -> newValue,
                        HashMap::new)).forEach(
                (k, v) -> {
                    if (!"0".equals(v)) {
                        SysPermission permission = new SysPermission();
                        permission.setResourceUrl(k.substring(1, k.length() - 1));
                        permission.setPermissionResourceName(v);
                        permission.setCreateBy("admin");
                        permission.setUpdateBy("admin");
                        permission.setResourceType(3);
                        permission.setCreateDate(DateUtil.getNowTimeStamp());
                        permission.setUpdateDate(DateUtil.getNowTimeStamp());
                        permission.setPermissionParentId(-1L);
                        try {
                            sysPermissionService.save(permission);
                            sysRolePermissionService.save(new SysRolePermission(1L, permission.getPermissionId()));
                        } catch (Throwable throwable) {
                            log.error("添加当前url的权限出现异常 " + k, throwable);
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        }
                    }
                }
        );

    }

}


