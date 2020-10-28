package me.fulln.base.model.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author fulln
 * @date 2019-06-13
 */
@Data
public class SysUserRole implements Serializable {

	/**
	 * id
	 */
	private Long id;
	/**
	 * 用户id
	 */
	@NotNull
	private Long userId;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 权限id
	 */
	private Long roleId;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 修改时间
	 */
	private Long updateTime;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 更新人
	 */
	private String updateBy;
	/**
	 * 是否删除
	 */
	private Integer isDelete;

}
