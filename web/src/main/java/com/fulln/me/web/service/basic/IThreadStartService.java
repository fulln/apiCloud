package com.fulln.me.web.service.basic;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.model.email.EmailEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

/**
 * @program: api
 * @description: 线程池启动业务接口
 * @author: fulln
 * @create: 2018-10-09 15:32
 * @Version： 0.0.1
 **/
@FeignClient(value = "${feign.url}",path = "${feign.path}")
public interface IThreadStartService {

    /**
     * 线程池全部关闭
     */
    @GetMapping("/threadStart/destroy")
    void destroy();

    @GetMapping("/threadStart/text")
    GlobalResult dispatch(String text);

    /**
     * 写file文件
     *
     * @param path    地址
     * @param context 文章内容
     */
    @GetMapping("/threadStart/fileCreate")
    void fileCreate(@RequestParam("path")String path, @RequestParam("context")String context);

    /**
     * 读取file文件
     */
    @GetMapping("/threadStart/fileReader")
    GlobalResult fileReader(File path);

    /**
     * 发送邮件
     */
    @GetMapping("/threadStart/sendEmail")
    void sendEmail(EmailEntity emailEntity);
}
