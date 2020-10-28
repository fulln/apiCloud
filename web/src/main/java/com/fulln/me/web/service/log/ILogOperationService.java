package com.fulln.me.web.service.log;


import me.fulln.base.model.log.LogOperationInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author fulln
 * @program api
 * @description 用户操作业务层
 * @Date 2019-01-23 15:27
 * @Version 0.0.1
 **/
@FeignClient(value = "${feign.url}",path = "${feign.path}")
public interface ILogOperationService {

    @PostMapping("/logOperate/save")
    Boolean save(LogOperationInfo info);

}
