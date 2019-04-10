package com.fulln.me.config.i18n;

import com.fulln.me.api.service.basic.ILanguageCloumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * @program: SpringCloud
 * @description: 代码国际化配置
 * @author: fulln
 * @create: 2018-07-20 14:51
 * @Version： 0.0.1
 **/

@Slf4j
@Service
public class LanguageCloumService implements ILanguageCloumService {

    @Resource
    private MessageSource messageSources;

    public String getCloums(String code) {

        Locale locale = LocaleContextHolder.getLocale();
        try {
            if(locale == null){
                return messageSources.getMessage(code, null, Locale.CHINA);
            }else{
                return messageSources.getMessage(code, null, locale);
            }
        }catch (Exception e){
            return messageSources.getMessage(code, null, Locale.CHINA);
        }


    }

    public String getCloums(String code, String text) {

        String output;

        if ( "zh".equals(text)) {
            output = messageSources.getMessage(code, null, Locale.CHINA);
        } else if ("en".equals(text)) {
            output = messageSources.getMessage(code, null, Locale.US);
        } else {
            output = null;
        }
        return output;

    }
}
