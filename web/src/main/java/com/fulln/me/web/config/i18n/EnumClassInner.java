package com.fulln.me.web.config.i18n;


import com.fulln.me.web.config.enums.GlobalEnums;
import com.fulln.me.web.config.enums.MessageEnums;
import com.fulln.me.web.service.basic.ILanguageCloumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

/**
 * @AUthor: fulln
 * @Description: 中英文转换
 * @Date : Created in  11:06  2018/12/8 0008.
 */
@Component
public class EnumClassInner {

    @Autowired
    private ILanguageCloumService cloumService;

    @PostConstruct
    public void postConstruct() {
        for (MessageEnums messages : EnumSet.allOf(MessageEnums.class)) {
            messages.setCloumService(cloumService);
        }
        for (GlobalEnums messages : EnumSet.allOf(GlobalEnums.class)) {
            messages.setCloumService(cloumService);
        }
    }
}