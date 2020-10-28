package me.fulln.tuc.model.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.fulln.tuc.enums.UserStatEnums;

import javax.persistence.*;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserBasic implements Serializable {

  private static final long serialVersionUID = -5567584069031018340L;

  @Id
  private Long userId;
  private String userName;
  private String userPass;
  private String nickName;
  private String userIcon;
  private String sex;
  @Enumerated
  private UserStatEnums stat;
  private Integer isEmailConfirmed;
  private Integer isPhoneConfirmed;
  private String createBy;
  private Long createDate;
  private Long updateDate;
  private String email;
  private String mobile;
  private String userSalt;
  private Integer roleId;

  @Transient
  private String currentToken;
}
