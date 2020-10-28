package me.fulln.tuc.model.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author fulln
 * @version 0.0.1
 * @program apiCloud
 * @description 用户行为表
 * @date 2019/6/12 22:43
 **/
@Data
@Document("sys_user_behavior")
public class SysUserBehavior {
    /**
     * 用户id
     */
    @Id
    private String  userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户最后登陆时间
     */
    private Long lastLoginDate;
    /**
     * 用户最后登陆ip
     */
    private String lastLoginIp;

    private Long createTime;
    private Long updateTime;
    private String createBy;
    private String updateBy;
    private Integer isDelete;

}
