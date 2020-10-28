package me.fulln.base.model.log;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@Data
public class LogLoginInfo implements Serializable {

  private static final long serialVersionUID = 8836048610180030722L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long logId;
  private String logUserName;
  private Long logUserLoginTime;
  private Long logUserLogoutTime;
  private String logUserLoginIp;
  private String remark;
  private Integer roleId;

}
