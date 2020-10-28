package me.fulln.base.model.sql;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : fulln
 * @serial :  创建sql的实体
 * @version : JDK 1.8
 * @date : Created in  15:19  2019/6/26.
 */
@Data
public class CreateTableVO implements Serializable {

    private static final long serialVersionUID = 4130991680886174155L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 注释
     */
    private String fieldDescribName;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 字段长度
     */
    private Integer fieldLength;

    /**
     * 小数位数
     */
    private Integer decimal;

    /**
     * 字段是否为空
     */
    private String isNull;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 主键索引
     */
    private Integer isPrimaryKey;

    /**
     * 备注
     */
    private String remark;
}