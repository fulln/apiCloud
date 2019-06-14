package com.fulln.me.web.config.shiro;


import com.fulln.me.api.model.system.SysPermission;
import com.fulln.me.api.model.user.SysUserBasic;
import com.fulln.me.web.service.system.ISysPermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;


/**
 * @AUthor: fulln
 * @Description:
 * * 用于url拦截
 *  * 权限 拦截策略()
 * @Date : Created in  14:58  2019/1/19.
 */

public class UrlPathMatchingFilter extends PathMatchingFilter {


    private ISysPermissionService permissionService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        //请求的url
        String requestURL = getPathWithinApplication(request);

        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            return true;
        }

        SysUserBasic sysUserBasic = (SysUserBasic) subject.getSession().getAttribute("currentUser");

        List<SysPermission> permissions = permissionService.loadUserResources(sysUserBasic.getRoleId());

        boolean hasPermission = permissions
                .stream()
                .anyMatch(permission -> permission.getResourceUrl().equals(requestURL));

        if (hasPermission) {
            return true;
        } else {
            UnauthorizedException ex = new UnauthorizedException("当前用户没有访问路径" + requestURL + "的权限");
            subject.getSession().setAttribute("ex", ex);
            return false;
        }
    }

    public ISysPermissionService getPermissionService() {
        return permissionService;
    }

    @Resource
    public void setPermissionService(ISysPermissionService permissionService) {
        this.permissionService = permissionService;
    }
}