package me.fulln.base.common.enums.downloads;


import me.fulln.base.common.enums.config.EnumsConfig;
import org.springframework.util.StringUtils;

import java.util.EnumSet;

/**
 * @AUthor: fulln
 * @Description: 下载用枚举
 * @Date : Created in  23:04  2018/11/7.
 */
public enum  SourceDownloadEnums implements EnumsConfig {

    PDF_MINE("mine", "/opt/source/about/MyDeerCV.pdf","李锋.pdf");

    public String code;
    public String datas;
    public String message;

    SourceDownloadEnums(String code, String datas, String message) {
        this.code = code;
        this.datas = datas;
        this.message = message;
    }

    public static SourceDownloadEnums getEnmus(String code){

        for (SourceDownloadEnums set :
                EnumSet.allOf(SourceDownloadEnums.class)) {
            if( !StringUtils.isEmpty(code) && code.equals(set.code)){
                return set;
            }
        }
                return null;
    }

}
