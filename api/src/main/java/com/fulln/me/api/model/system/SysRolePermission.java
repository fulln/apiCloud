package com.fulln.me.api.model.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysRolePermission {

  private Long roleId;
  private Long permissionId;


}
