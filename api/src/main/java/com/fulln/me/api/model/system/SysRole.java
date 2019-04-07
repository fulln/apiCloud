package com.fulln.me.api.model.system;


import com.fulln.me.api.common.enums.RoleStatusEnums;
import lombok.Data;

import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class SysRole implements Serializable {

  private static final long serialVersionUID = -8327431104601493039L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer roleId;
  private String roleName;
  @Enumerated
  private RoleStatusEnums roleStatus;
  private String roleRemarks;
  private String roleCode;
  private String createBy;
  private Long createDate;
  private String updateBy;
  private Long updateDate;


}
