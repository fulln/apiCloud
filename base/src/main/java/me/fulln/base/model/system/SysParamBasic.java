package me.fulln.base.model.system;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysParamBasic implements Serializable {

  private static final long serialVersionUID = 603947013505665655L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String param;
  private String paramName;
  private String paramValue;
  private Long createDate;
  private Long updateDate;
  private String remarks;


}
