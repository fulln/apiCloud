package com.fulln.me.service.basic.impl;


import base.BaseTest;
import com.fulln.me.api.common.constant.ConstantAll;
import com.fulln.me.api.common.utils.AesUtil;
import com.fulln.me.api.model.email.EmailEntity;
import com.fulln.me.service.basic.IMailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MailServiceImplTest extends BaseTest {

    @Autowired
    private IMailService mailService;

    @Test
    public void sendHtmlMail() {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setReceiver("a114mypr1nt@gmail.com");
        emailEntity.setSubject(ConstantAll.EMAIL_FOR_REIGISIT_SUBJECT);
        String registerCode = AesUtil.AESEncode(ConstantAll.EMAIL_FOR_REIGISIT_RECIVE_USER + 123);
        emailEntity.setText(String.format("您正在fulln.me上注册用户,请点击以下链接确认是本人,此链接5分钟内有效,如果不是本人操作请忽略当前邮件</br></br>http://back.fulln.me/register/%s", registerCode));
        mailService.sendHtmlMail(emailEntity);
    }

}