package com.fulln.me.web.service.basic;


import me.fulln.base.model.email.EmailEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: springcloud
 * @description: 邮件发送
 * @author: fulln
 * @create: 2018-06-29 11:32
 **/
@FeignClient(value = "${feign.url}",path = "${feign.path}")
public interface IMailService {

    /**
     * @Author: fulln
     * @Description 发送邮件
     * @para: email
     * @retun: a
     * @Date: 2018/7/6 0006-13:32
     */
    @GetMapping("/mail/send")
    void sendHtmlMail(EmailEntity email);


}
