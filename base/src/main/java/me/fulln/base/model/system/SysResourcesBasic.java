package me.fulln.base.model.system;

import lombok.Data;

import java.io.Serializable;

/**
 * @AUthor: fulln
 * @Description:
 * @Date : Created in  0:15  2018/10/12.
 */
@Data
public class SysResourcesBasic implements Serializable {

    private static final long serialVersionUID = 4965369776152952626L;


    private String resourceName;

    private Integer id;
    /**
     *上级菜单
     */
    private String upperName;
    /**
     * code
     */
    private String resourceUrl;




}
