package com.fulln.me.service.basic.impl;


import com.fulln.me.api.model.email.EmailEntity;
import com.fulln.me.constant.MailConfig;
import com.fulln.me.service.basic.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 * @program: SpringCloud
 * @description: 邮件发送实现类
 * @author: fulln
 * @create: 2018-06-29 11:39
 **/
@Service
@Slf4j
public class MailServiceImpl implements IMailService {


    @Resource
    private MailConfig mailConfig;

    private static JavaMailSenderImpl sender;

    /**
     * @Author: fulln
     * @Description:邮件发送业务处理
     * @param: [email]
     * @return: com.fulln.GlobalResult
     * @Date: 2018/7/6 0006-13:33
     */
    @Override
    public void sendHtmlMail(EmailEntity email) {

        if (sender == null) {
            sender = getMailSender();
        }

        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = sender.createMimeMessage();
        try {
            // 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
            // multipart模式 为true时发送附件 可以设置html格式
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");

            messageHelper.setFrom(mailConfig.getAccount());

            // 设置收件人
            if (email.getReceiver() != null) {
                String receiver = email.getReceiver();
                String[] receivers = receiver.split(",");
                messageHelper.setTo(receivers);
            }

            //设置主题
            messageHelper.setSubject(email.getSubject());

            //设置抄送地址，多个以,分隔
            if (email.getCcUser() != null) {
                InternetAddress[] ccAddress = InternetAddress.parse(email.getCcUser());
                messageHelper.setCc(ccAddress);
            }

            //设置密送地址
            if (email.getBccUser() != null) {
                InternetAddress[] bccAddress = InternetAddress.parse(email.getBccUser());
                messageHelper.setCc(bccAddress);
            }

            //发送日期
            messageHelper.setSentDate(new Date());


            //添加附件
            if (email.getAttachment() != null && email.getAttachment().length != 0) {

                for (String f : email.getAttachment()
                ) {
                    FileSystemResource file = new FileSystemResource(new File(f));
                    String[] paths = f.split("\\\\|/");
                    String fileName = paths[paths.length - 1];
                    messageHelper.addAttachment(fileName, file);
                }
            }
            // true 表示启动HTML格式的邮件 发送正文
            messageHelper.setText(
                    "<html><head></head><body>" +
                            email.getText()
                            + "</body></html>"
                    , true);

            //正文中插入图片
            if (email.getImgPath() != null) {
                FileSystemResource img = new FileSystemResource(new File(email.getImgPath()));
                messageHelper.addInline("pic", img);
            }

            // 发送邮件
            sender.send(mailMessage);
        } catch (MessagingException e) {
            log.error("邮件发送", e);
        } catch (Exception e) {
            log.error("系统异常", e);
        }
    }


    /**
     * @Author: fulln
     * @Description:初始化邮件发送者
     * @param: []
     * @return: org.springframework.mail.javamail.JavaMailSenderImpl
     * @Date: 2018/7/6 0006-11:57
     */
    private JavaMailSenderImpl getMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailConfig.getHost());
        javaMailSender.setUsername(mailConfig.getAccount());
        javaMailSender.setPassword(mailConfig.getPassword());
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", mailConfig.getIsAuth());
        properties.put("mail.smtp.timeout", mailConfig.getOutTime());
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }


}
