package com.fulln.me.web.config.shiro;


import com.fulln.me.api.common.utils.DateUtil;
import com.fulln.me.api.model.log.LogLoginInfo;
import com.fulln.me.web.service.log.ILogLoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author fulln
 * @version 0.0.1
 * @program api
 * @description 自定 shiro logoutfilter
 * @date 2018-11-16 09:22
 **/

public class DefaultLogoutFilter extends LogoutFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        Session se  =  SecurityUtils.getSubject().getSession();
        try {
            LogLoginInfo loginInfo = new LogLoginInfo();
            loginInfo.setLogUserLogoutTime(DateUtil.getNowTimeStamp());
            loginInfo.setLogId(Long.parseLong(se.getAttribute("userLogId")+""));
            logLoginService.update(loginInfo);
        } catch (Exception ise) {
            ise.printStackTrace();
        }finally {
            subject.logout();
        }

        issueRedirect(request, response, redirectUrl);
        //返回false表示不执行后续的过滤器，直接返回跳转到登录页面
        return false;
    }

    private ILogLoginService logLoginService;

    public ILogLoginService getLogLoginService() {
        return logLoginService;
    }
    @Autowired
    public void setLogLoginService(ILogLoginService logLoginService) {
        this.logLoginService = logLoginService;
    }
}
