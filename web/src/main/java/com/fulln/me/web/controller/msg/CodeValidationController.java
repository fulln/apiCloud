package com.fulln.me.web.controller.msg;


import com.fulln.me.api.common.entity.GlobalResult;
import com.fulln.me.web.service.basic.CodeValidationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: api
 * @description: 接码平台控制层
 * @author: fulln
 * @create: 2018-09-25 09:05
 * @Version： 0.0.1
 **/
@RequestMapping("/validate")
@RestController
@Api(value = "接码平台的控制层", tags = "接码平台api")
public class CodeValidationController {

    @Resource
    private CodeValidationService validationService;


    @PostMapping("/login")
    @ApiOperation(value = "登录接码平台")
    public GlobalResult loginPlatform() {
        return validationService.tokenLogin();
    }


    @ApiImplicitParam(value = "code", name = "平台返回的token")
    @ApiOperation(value = "接收接码平台的手机号")
    @PostMapping("/phone")
    public GlobalResult phone(@RequestParam String code) {
        return validationService.tokenPhone(code);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(value = "code", name = "平台返回的token"),
            @ApiImplicitParam(value = "phoneNo", name = "平台返回的手机号")
    })
    @ApiOperation(value = "接收接码平台的验证码")
    @PostMapping("/Token")
    public GlobalResult phoneToken(@RequestParam String code, String phoneNo) {
        return validationService.getPhoneCode(code, phoneNo);
    }
}
