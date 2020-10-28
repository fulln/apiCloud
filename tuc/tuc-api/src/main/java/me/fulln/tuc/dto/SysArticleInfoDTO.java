package me.fulln.tuc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.fulln.base.common.entity.BaseEntity;
import me.fulln.tuc.enums.ArticleStatusEnums;
import me.fulln.tuc.model.user.SysUserBasic;

import java.io.Serializable;

/**
 * @program: api
 * @description: 文章查询类
 * @author: fulln
 * @create: 2018-11-13 17:05
 * @Version： 0.0.1
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class SysArticleInfoDTO extends BaseEntity implements Serializable {


    private static final long serialVersionUID = -3071742448558303333L;

    private Long id;
    private String articleName;
    private String articleTag;
    private String articleType;
    private String articleFileName;
    private String articleFilePath;
    private ArticleStatusEnums articlePushStatus;
    private String articleContent;
    private Long createTime;
    private Long updateTime;
    private String custNo;
    private SysUserBasic user;

}
