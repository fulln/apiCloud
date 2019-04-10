package com.fulln.me.api.service.basic;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 代码国际化配置接口
 * @date 2019/4/8 21:43
 **/
public interface ILanguageCloumService {

    String getCloums(String code);

    String getCloums(String code, String text);
}
