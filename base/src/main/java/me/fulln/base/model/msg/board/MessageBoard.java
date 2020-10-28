package me.fulln.base.model.msg.board;

import me.fulln.base.common.enums.mongo.QueryType;
import me.fulln.base.common.annotation.MongoField;
import me.fulln.base.common.entity.BaseEntity;
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
