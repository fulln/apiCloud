package com.fulln.me.api.model.msg.board;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 留言板实体类
 * @date 2019/5/22 23:18
 **/
@Data
public class MessageBoard implements Serializable {

    @Id
    private Integer userId;
    private String userName;
    private String messageContent;
    private Long createTime;
    private Long updateTime;
    private String createBy;
    private String updateBy;



}
