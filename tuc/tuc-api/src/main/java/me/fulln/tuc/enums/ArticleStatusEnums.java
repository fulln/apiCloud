package me.fulln.tuc.enums;


import lombok.AllArgsConstructor;
import me.fulln.base.common.enums.config.EnumsConfig;

/**
 * @AUthor: fulln
 * @Description:  文章状态枚举
 * @Date : Created in  23:53  2018/11/16.
 */
@AllArgsConstructor
public enum ArticleStatusEnums implements EnumsConfig {
    /**
     *
     */
    SAVED(0,"保存"),
    PUSHED(1,"发布"),
    DELETED(2,"取消");

    private int code;
    private String  desc;


}
