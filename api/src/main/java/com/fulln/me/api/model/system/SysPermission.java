package com.fulln.me.api.model.system;



import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class SysPermission implements Serializable {

  private static final Long serialVersionUID = -5160029918017445909L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long permissionId;
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
  private String permissionNo;


}
