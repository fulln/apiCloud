package com.fulln.me.api.model.msg.board;

import com.fulln.me.api.common.annotation.MongoField;
import com.fulln.me.api.common.entity.BaseEntity;
import com.fulln.me.api.common.enums.mongo.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 留言板实体类
 * @date 2019/5/22 23:18
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageBoard extends BaseEntity implements Serializable {


    private Long messageId;
    @MongoField(type = QueryType.LIKE)
    private String messageContent;
    @MongoField
    private Long createTime;
    private Long updateTime;
    @MongoField
    private String createBy;
    private String updateBy;
    @MongoField
    private Integer isDelete;

    @MongoField(attribute = "createTime",type = QueryType.GTE)
    private Long startTime;
    @MongoField(attribute = "createTime",type = QueryType.LTE)
    private Long endTime;
}
