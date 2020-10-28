package me.fulln.base.model.system.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * es 查询实体
 */
//@Document(indexName = "permission", type = "permission")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysPermissionDTO implements Serializable {


    private static final long serialVersionUID = -589360468084534315L;

    @Id
    private Long id;
    private Long permissionId;
    //    @Field(type = FieldType.Keyword, searchAnalyzer = "ik_max_word", analyzer = "ik_smart")
    private String permissionResourceName;
    private Long permissionSort;
    private String permissionState;
    private String permissionRemarks;
    private Long updateDate;
    private String updateBy;
    private Long createDate;
    private String createBy;
    private Integer resourceType;
    private String resourceUrl;
    private Long permissionParentId;


}
