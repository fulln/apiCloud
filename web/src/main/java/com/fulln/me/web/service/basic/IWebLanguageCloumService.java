package com.fulln.me.web.service.basic;

import com.fulln.me.api.service.basic.ILanguageCloumService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 代码国际化配置接口
 * @date 2019/4/8 21:43
 **/
@FeignClient(value = "${feign.url}")
public interface IWebLanguageCloumService extends ILanguageCloumService {

    String getCloums(String code);

    String getCloums(String code, String text);
}
