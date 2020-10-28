package com.fulln.me.web.controller.system.user;

import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.model.user.SysUserBasic;
import com.fulln.me.web.service.system.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 用户控制层
 * @date 2019/6/10 23:14
 **/
@Controller
@Slf4j
@RequestMapping("/registered")
public class RegisteredController {

    @Resource
    private ISysUserService sysUserService;

    @GetMapping
    public String registered() {
        return "regist";
    }

    @PostMapping("/save")
    @ResponseBody
    public GlobalResult saveUser(SysUserBasic userBasic) {
        return sysUserService.saveUser(userBasic);
    }

    @GetMapping("/{registerCode}")
    public void confirmCOde(@PathVariable String registerCode, HttpServletResponse response) {
        GlobalResult result = sysUserService.CheckUserByEmail(registerCode);
        try {
            response.setContentType("text/html;charset=utf-8");
            if (result.getCode() > 0) {
                response.getWriter().write("<script>alert('注册成功!');</script>");
            } else {
                response.getWriter().write("<script>alert('注册失败!" + result.getMessage() + "');</script>");

            }
            response.getWriter().write("<script>window.location.href='http://localhost:8082/web/' ;</script>");
            response.getWriter().flush();
        } catch (IOException e) {
            log.error("注册邮件回传时发生异常",e);
            try {
                response.sendRedirect("/web/");
            } catch (IOException ex) {
                log.error("注册邮件回传时发生异常",e);
            }
        }

    }


}
