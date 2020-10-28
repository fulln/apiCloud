package me.fulln.base.common.i18n;


import me.fulln.base.common.enums.GlobalEnums;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.EnumSet;

/**
 * @AUthor: fulln
 * @Description: 中英文转换
 * @Date : Created in  11:06  2018/12/8 0008.
 */
@Component
public class EnumClassInner {

    @Resource
    private MessageSource messageSources;


    @PostConstruct
    public void postConstruct() {
        LanguageFieldService languageFieldService = new LanguageFieldService();
        languageFieldService.setMessageSources(messageSources);
        for (GlobalEnums messages : EnumSet.allOf(GlobalEnums.class)) {
            messages.setFieldService(languageFieldService);
        }
    }
}