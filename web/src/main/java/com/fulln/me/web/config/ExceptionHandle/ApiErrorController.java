package com.fulln.me.web.config.ExceptionHandle;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.api.common.enums.GlobalEnums;
import com.fulln.me.api.common.utils.LinuxSystemUtil;
import com.fulln.me.api.model.email.EmailEntity;
import com.fulln.me.web.service.basic.IThreadStartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * @AUthor: fulln
 * @Description:统一异常的处理
 * @Date : Created in  20:43  2018/9/1.
 */
@Slf4j
@RestControllerAdvice
public class ApiErrorController {

    @Value("com.fulln.api.mail.sendAddr")
    private String addr;

    @Resource
    private IThreadStartService startService;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public GlobalResult handleMyException(HttpServletRequest request, Exception e) {
        log.error("未捕捉的系统异常:", e);
        try {
            String platform =System.getProperty("spring.profile.active");
            if (!StringUtils.isEmpty(platform) && "pro".equals(platform)) {
                EmailEntity emailEntity = new EmailEntity();
                emailEntity.setSubject("服务器异常提醒," + LinuxSystemUtil.getThisProjectName() + "项目");
                emailEntity.setReceiver(addr);
                emailEntity.setText("异常报错！");


                float cpuf = LinuxSystemUtil.getCpuInfo();
                int[] mens = LinuxSystemUtil.getMemInfo();
                LinuxSystemUtil.Desk desk = LinuxSystemUtil.getDeskUsage();
                String ip = LinuxSystemUtil.getHostIp();

                float meni = (float) (mens[0] - mens[1] - mens[4]) / mens[0];
                meni = Math.round(meni * 100) / 100f;

                Map<String, Object> maps = new HashMap<>(3);
                maps.put("cup占用比", cpuf + "");
                maps.put("memory内存占用比", meni + "");
                maps.put("disk", desk);

                emailEntity.setText("服务器: " + ip);
                for (Map.Entry entry : maps.entrySet()) {
                    emailEntity.setText(emailEntity.getText() + entry.getKey() + ":" + entry.getValue().toString() + ";");
                }
                startService.sendEmail(emailEntity);
            }
        } catch (Exception e2) {
            log.error("返回当前异常报告出现错误", e2);
        }

        return GlobalEnums.SYS_ERROR.results();
    }

}
