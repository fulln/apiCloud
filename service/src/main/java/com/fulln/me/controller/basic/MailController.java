package com.fulln.me.controller.basic;


import me.fulln.base.model.email.EmailEntity;
import com.fulln.me.service.basic.IMailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: springcloud
 * @description: 邮件发送
 * @author: fulln
 * @create: 2018-06-29 11:32
 **/
@RestController
@RequestMapping("/mail")
public class MailController {

    @Resource
    private IMailService mailService;

    /**
     * @Author: fulln
     * @Description 发送邮件
     * @para: email
     * @retun: a
     * @Date: 2018/7/6 0006-13:32
     */
    @GetMapping("/send")
    public void sendHtmlMail(EmailEntity email) {
        mailService.sendHtmlMail(email);
    }


}
