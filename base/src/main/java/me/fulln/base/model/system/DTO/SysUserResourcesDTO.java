package me.fulln.base.model.system.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * @AUthor: fulln
 * @Description:查询类
 * @Date : Created in  0:20  2018/10/12.
 */

@Data
public class SysUserResourcesDTO implements Serializable {

    private static final long serialVersionUID = -8208055240109669673L;

    private Integer id;
    private Integer userId;
    private Integer resourcesId;
    private String resourcesUrl;

}
