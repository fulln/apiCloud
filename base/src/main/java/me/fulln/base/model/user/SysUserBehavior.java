package me.fulln.base.model.user;

import me.fulln.base.common.annotation.MongoField;
import lombok.Data;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 用户行为表
 * @date 2019/6/12 22:43
 **/
@Data
public class SysUserBehavior {
    /**
     * 用户id
     */
    @MongoField
    private Long userId;
    /**
     * 用户姓名
     */
    @MongoField
    private String userName;
    /**
     * 用户最后登陆时间
     */
    @MongoField
    private Long lastLoginDate;
    /**
     * 用户最后登陆ip
     */
    @MongoField
    private String lastLoginIp;

    private Long createTime;
    private Long updateTime;
    private String createBy;
    private String updateBy;
    private Integer isDelete;

}
