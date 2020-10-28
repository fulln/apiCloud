package me.fulln.base.common.i18n;


import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @program: SpringCloud
 * @description: 代码国际化配置
 * @author: fulln
 * @create: 2018-07-20 14:51
 * @Version： 0.0.1
 **/
public class LanguageFieldService {


    private MessageSource messageSources;

    public String getField(String code) {

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

    public String getField(String code, String text) {

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

    public MessageSource getMessageSources() {
        return messageSources;
    }

    public void setMessageSources(MessageSource messageSources) {
        this.messageSources = messageSources;
    }
}
