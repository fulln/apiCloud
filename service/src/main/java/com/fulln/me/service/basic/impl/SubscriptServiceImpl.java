package com.fulln.me.service.basic.impl;


import com.fulln.me.api.common.enums.GlobalEnums;
import com.fulln.me.api.common.enums.downloads.SourceDownloadEnums;
import com.fulln.me.service.basic.ISubscriptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author fulln
 * @version 0.0.1
 * @program api
 * @description 文档下载业务层实现
 * @date 2019-01-08 11:48
 **/
@Service
@Slf4j
public class SubscriptServiceImpl implements ISubscriptService {

    /**
     * @author : fulln
     * @description :
     * @date : Created in 2019/1/19 .
     * @params : [mine, response]
     * @retrun : void
     */
    @Override
    public void downloads(String mine, HttpServletResponse response) {

       
        
        SourceDownloadEnums downloadEnums = SourceDownloadEnums.getEnmus(mine);

        if (downloadEnums != null) {
            String headerKey = "Content-Disposition";
            String headerValue = null;
            try {
                headerValue = String.format("attachment; filename=\"%s\"",
                        URLEncoder.encode(downloadEnums.message, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("格式转码出现异常");
            }
            response.setHeader(headerKey, headerValue);
            try (InputStream myStream = this.getClass().getClassLoader().getResourceAsStream(downloadEnums.datas)) {
                if (myStream != null) {
                    IOUtils.copy(myStream, response.getOutputStream());
                }
                response.flushBuffer();
            } catch (IOException e) {
                log.error("文件读取失败!", e);
            }
        } else {
            try {
                response.getOutputStream().write( GlobalEnums.QUERY_EMPTY.results().toString().getBytes());
                log.error("未找到对应的文件{}", mine);
                response.flushBuffer();
            } catch (IOException e) {
                log.error("关闭流异常", e);
            }
        }
    }
}
