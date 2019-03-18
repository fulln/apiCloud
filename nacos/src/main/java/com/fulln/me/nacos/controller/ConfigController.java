package com.fulln.me.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("config")
public class ConfigController {

    @NacosValue(value = "123", autoRefreshed = true)
    private String useLocalCache;

    @GetMapping(value = "/get")
    public String get() {
        return useLocalCache;
    }
}