package com.fulln.me.thread;

import com.fulln.me.api.common.threadconfig.AbstractThreadRunFactory;
import com.fulln.me.api.model.email.EmailEntity;
import com.fulln.me.api.service.basic.IMailService;

/**
 * @author fulln
 * @version 0.0.1
 * @program api
 * @description 邮件发送的线程
 * @date 2019-01-25 14:43
 **/
public class EmailSendTask extends AbstractThreadRunFactory {

    private IMailService mailService;
    private EmailEntity emailEntity;

    public EmailSendTask(IMailService mailService, EmailEntity emailEntity) {
        this.mailService = mailService;
        this.emailEntity = emailEntity;
    }

    @Override
    public void run() {
        mailService.sendHtmlMail(emailEntity);
    }

}
