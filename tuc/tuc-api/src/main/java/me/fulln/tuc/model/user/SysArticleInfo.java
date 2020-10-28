package me.fulln.tuc.model.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.fulln.tuc.enums.ArticleStatusEnums;

import java.io.Serializable;

/**
 * @AUthor: fulln
 * @Description:
 * @Date : Created in  15:47  2019/1/19.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysArticleInfo implements Serializable {

    private static final long serialVersionUID = 5562300375538452635L;

    private Long id;
    private String articleName;
    private String articleTag;
    private String articleType;
    private String articleFileName;
    private String articleFilePath;
    private String articleContent;
    private Long createTime;
    private String remarks;
    private Long updateTime;
    private String custNo;
    private ArticleStatusEnums articlePushStatus;

}
