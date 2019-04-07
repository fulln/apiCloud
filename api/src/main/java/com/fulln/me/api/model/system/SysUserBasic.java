package com.fulln.me.api.model.system;


import com.fulln.me.api.common.enums.UserStatEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserBasic implements Serializable {

  private static final long serialVersionUID = -5567584069031018340L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userId;
  private String userName;
  private String userPass;
  private String nickName;
  private String userIcon;
  private String sex;
  @Enumerated
  private UserStatEnums stat;
  private Long lastLoginDate;
  private String lastLoginIp;
  private String isDel;
  private String isEmailConfirmed;
  private String isPhoneConfirmed;
  private String createBy;
  private Long createDate;
  private Long updateDate;
  private Integer loginFailCounts;
  private String email;
  private String mobile;
  private Integer roleId;
  private String userSalt;
  private String custNo;
  @Transient
  private String currentToken;

}
