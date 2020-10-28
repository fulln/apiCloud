package me.fulln.base.model.search;


import me.fulln.base.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @AUthor: fulln
 * @Description:种子实体类
 * @Date : Created in  22:44  2018/9/1.
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class UserTorrents extends BaseEntity {

    private static final long serialVersionUID = -319118410988265994L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer torrentId;
    private String torrentData;
    private String torrentType;
    private Long createTime;
    private Long updateTime;
    private String createBy;
    private String updateBy;

}
