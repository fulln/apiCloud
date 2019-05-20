package com.fulln.me.api.common.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * @AUthor: fulln
 * @Description: 通用查询条件
 * @Date : Created in  12:51  2019/1/19.
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1657904443903319591L;

    private Integer pageSize;
    private Integer pageNo;
    private String startTime;
    private String endTime;


}
