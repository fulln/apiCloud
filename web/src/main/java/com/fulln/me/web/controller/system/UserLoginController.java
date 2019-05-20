package com.fulln.me.web.controller.system;


import com.fulln.me.api.common.enums.GlobalEnums;
import com.fulln.me.api.common.exception.CustomerLockAccountException;
import com.fulln.me.api.common.exception.DisableCustomerException;
import com.fulln.me.api.common.utils.Captcha;
import com.fulln.me.api.common.utils.DateUtil;
import com.fulln.me.api.model.log.LogLoginInfo;
import com.fulln.me.api.model.system.SysUserBasic;
import com.fulln.me.web.config.base.method.BaseController;
import com.fulln.me.web.config.redis.RedisUtil;
import com.fulln.me.web.service.log.ILogLoginService;
import com.fulln.me.web.service.system.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.fulln.me.api.common.utils.RequestIpUtil.getIpAddress;


/**
 * @program: api
 * @description: 登录接口
 * @author: fulln
 * @create: 2018-10-23 17:47
 * @Version： 0.0.1
 **/
@Controller
@Slf4j
public class UserLoginController extends BaseController {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ILogLoginService logLoginService;

    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginIn(String userName, String password, HttpServletRequest request, Model model, HttpSession session, String token) {
        Subject subject = SecurityUtils.getSubject();
        String reqVerifyCode = WebUtils.getCleanParam(request, "code");
        //  判断是不是获取token进行的登陆
        if (StringUtils.isEmpty(password)) {
            if (StringUtils.isEmpty(token) || !redisUtil.hasKey(token)) {
                //干脆什么都没有输入,判断下是不是当前session中已经登陆过一次了
                if (subject.isAuthenticated()) {
                    model.addAttribute("loadNum", 0);
                    return "play/play";
                } else {
                    model.addAttribute("message", GlobalEnums.USER_HAS_NO_LOGIN.results().getMessage());
                    return "login";
                }
            } else {
                //判断token中代表的用户有没有登陆
                if (getUser() == null) {
                    model.addAttribute("message", GlobalEnums.USER_HAS_NO_LOGIN.results().getMessage());
                    return "login";
                } else {
                    model.addAttribute("loadNum", 0);
                    return "play/play";
                }
            }
        }

        //后台生成的code
        String verifyCode = (String) redisUtil.get(request.getSession().getId());
        if (!StringUtils.isEmpty(verifyCode)) {
            //前端传过来的code
            if (StringUtils.isEmpty(reqVerifyCode)) {
                model.addAttribute("message", GlobalEnums.LOGIN_INPUT_CODE.results().getMessage());
                return "login";
            } else if (!StringUtils.substringMatch(verifyCode, 1, reqVerifyCode.toLowerCase())) {
                model.addAttribute("message", GlobalEnums.ERROR_INPUT_CODE.results().getMessage());
                return "login";
            }
        }
        UsernamePasswordToken tokens = new UsernamePasswordToken(userName, password);
        tokens.setRememberMe(true);

        try {
            subject.login(tokens);
            if (subject.isAuthenticated()) {
                SysUserBasic currentUser = getUser();
                currentUser.setLastLoginIp(getIpAddress(request));
                currentUser.setLoginFailCounts(0);
                sysUserService.Update(currentUser);
                LogLoginInfo loginInfo = new LogLoginInfo();
                loginInfo.setLogUserLoginIp(getIpAddress(request));
                loginInfo.setLogUserLoginTime(DateUtil.getNowTimeStamp());
                loginInfo.setLogUserName(currentUser.getUserName());
                loginInfo.setRoleId(currentUser.getRoleId());
                logLoginService.insert(loginInfo);
                session.setAttribute("userLogId",loginInfo.getLogId());
                model.addAttribute("loadNum", 0);
                return "play/play";
            }
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("message", GlobalEnums.ERROR_INPUT_PASS.results().getMessage());
        } catch (ExcessiveAttemptsException e) {
            model.addAttribute("message", GlobalEnums.LOGIN_MAX_FAIL.results().getMessage());
        } catch (LockedAccountException e) {
            model.addAttribute("message", GlobalEnums.ERROR_ACCOUNT_LOCK.results().getMessage());
        } catch (DisabledAccountException e) {
            model.addAttribute("message", GlobalEnums.ERROR_ACCOUNT_DISPLAY.results().getMessage());
        } catch (ExpiredCredentialsException e) {
            model.addAttribute("message", GlobalEnums.ERROR_ACCOUNT_UNACTIVATED.results().getMessage());
        } catch (UnknownAccountException e) {
            model.addAttribute("message", GlobalEnums.LOGIN_UNKNOWN_ACCOUNT.results().getMessage());
        } catch (CustomerLockAccountException e) {
            model.addAttribute("message", GlobalEnums.ROLE_ACCOUNT_LOCKED.results().getMessage());
        } catch (DisableCustomerException e) {
            model.addAttribute("message", GlobalEnums.ROLE_ACCOUNT_DELETE.results().getMessage());
        }
        return "login";
    }

    @GetMapping("/code")
    public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置页面不缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        Captcha ca = new Captcha();
        redisUtil.set(request.getSession().getId(),ca.getCode(),60);
        ImageIO.write(ca.getImage(), "JPEG", response.getOutputStream());
    }


}
