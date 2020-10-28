package me.fulln.base.model.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 邮件entity
 * @author Administrator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailEntity implements Serializable {

    private static final long serialVersionUID = 1785196060454970354L;


    /**
     * 收件人 (多人以,隔开)
     */
	private String receiver;
    /**
     * 标题
     */
    private String subject;
    /**
     * 模板
     */
    private String text;
    /**
     * 添加抄送人 (多人以,隔开)
     */
    private String ccUser;
    /**
     * 密送人(多人以,隔开)
     */
    private String bccUser;
    /**
     * 附件地址
     */
    private String[] attachment;

    /**
     * 邮件中发送的照片地址
     */
    private String imgPath;



}
