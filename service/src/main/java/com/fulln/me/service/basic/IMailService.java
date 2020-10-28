package com.fulln.me.service.basic;


import me.fulln.base.model.email.EmailEntity;

/**
 * @program: springcloud
 * @description: 邮件发送
 * @author: fulln
 * @create: 2018-06-29 11:32
 **/
public interface IMailService {

    /**
     * @Author: fulln
     * @Description 发送邮件
     * @para: email
     * @retun: a
     * @Date: 2018/7/6 0006-13:32
     */
    void sendHtmlMail(EmailEntity email);


}
