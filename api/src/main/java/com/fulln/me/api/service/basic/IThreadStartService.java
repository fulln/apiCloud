package com.fulln.me.api.service.basic;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.email.EmailEntity;

import java.io.File;

/**
 * @program: api
 * @description: 线程池启动业务接口
 * @author: fulln
 * @create: 2018-10-09 15:32
 * @Version： 0.0.1
 **/
public interface IThreadStartService {

    /**
     * 线程池全部关闭
     */
    void destroy();


    GlobalResult dispatch(String text);

    /**
     * 写file文件
     * @param path 地址
     * @param context 文章内容
     */
    void fileCreate(String path, String context);

    /**
     * 读取file文件
     */
    GlobalResult fileReader(File path);

    /**
     *  发送邮件
     */
    void sendEmail(EmailEntity emailEntity);
}
