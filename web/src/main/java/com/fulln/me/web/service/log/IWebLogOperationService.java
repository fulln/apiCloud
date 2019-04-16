package com.fulln.me.web.service.log;


import com.fulln.me.api.model.log.LogOperationInfo;
import com.fulln.me.api.service.log.ILogOperationService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author fulln
 * @program api
 * @description 用户操作业务层
 * @Date 2019-01-23 15:27
 * @Version 0.0.1
 **/
@FeignClient("${feign.url}")
public interface IWebLogOperationService extends ILogOperationService {

    Boolean save(LogOperationInfo info);

}
