package com.fulln.me.web.service.basic;


import com.fulln.me.api.model.email.EmailEntity;
import com.fulln.me.api.service.basic.IMailService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: springcloud
 * @description: 邮件发送
 * @author: fulln
 * @create: 2018-06-29 11:32
 **/
@FeignClient("${feign.url}")
public interface IWebMailService extends IMailService {

    /**
     * @Author: fulln
     * @Description 发送邮件
     * @para: email
     * @retun: a
     * @Date: 2018/7/6 0006-13:32
     */
    void sendHtmlMail(EmailEntity email);


}
