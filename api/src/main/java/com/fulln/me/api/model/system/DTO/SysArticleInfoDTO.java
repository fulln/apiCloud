package com.fulln.me.api.model.system.DTO;

import com.fulln.me.api.common.entity.BaseEntity;
import com.fulln.me.api.model.system.cloums.ArticleStatusEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
public class SysArticleInfoDTO extends BaseEntity {


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

}
