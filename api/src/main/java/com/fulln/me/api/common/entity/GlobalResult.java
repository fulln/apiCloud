package com.fulln.me.api.common.entity;


import lombok.Data;

/**
 * @Author: fulln
 * @Description: 通用当前返回结果
 * @Date: Created in 2018/5/2 0002
 */
@Data
public class GlobalResult {

    private String message;

    private Integer code;

    private Object datas;
}
