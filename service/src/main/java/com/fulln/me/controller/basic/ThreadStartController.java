package com.fulln.me.controller.basic;


import me.fulln.base.common.entity.GlobalResult;
import me.fulln.base.model.email.EmailEntity;
import com.fulln.me.service.basic.IThreadStartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;

/**
 * @program: api
 * @description: 线程池启动业务接口
 * @author: fulln
 * @create: 2018-10-09 15:32
 * @Version： 0.0.1
 **/
@RestController
@RequestMapping("/threadStart")
public class ThreadStartController {

    @Resource
            private IThreadStartService threadStartService;

    /**
     * 线程池全部关闭
     */
    @GetMapping("/destroy")
    public void destroy() {
        threadStartService.destroy();
    }

    @GetMapping("/text")
    public GlobalResult dispatch(String text) {
        return threadStartService.dispatch(text);
    }

    /**
     * 写file文件
     *
     * @param path    地址
     * @param context 文章内容
     */
    @GetMapping("/fileCreate")
    public void fileCreate(String path, String context) {
        threadStartService.fileCreate(path, context);
    }

    /**
     * 读取file文件
     */
    @GetMapping("/fileReader")
    public GlobalResult fileReader(File path) {
        return threadStartService.fileReader(path);
    }

    /**
     * 发送邮件
     */
    @GetMapping("/sendEmail")
    public void sendEmail(EmailEntity emailEntity) {
        threadStartService.sendEmail(emailEntity);
    }

}
