package com.fulln.me.config.handle;


import me.fulln.base.common.enums.config.CloumsEnumsConfig;
import me.fulln.base.model.system.cloums.ArticleStatusEnums;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @AUthor: fulln
 * @Description: 后端转database
 * @Date : Created in  15:42  2018/11/24 0024.
 */
@MappedTypes({ArticleStatusEnums.class})
@MappedJdbcTypes({JdbcType.INTEGER})
public  class DefaultEnumTypeHandler<E  extends  Enum<E> & CloumsEnumsConfig> extends BaseTypeHandler<E> {

    private Class<E> type;
    private E[] enums;

    public DefaultEnumTypeHandler(Class<E> type) {

        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        } else {
            this.type  = type;
            this.enums = type.getEnumConstants();
            if (this.enums == null) {
                throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
            }
        }

    }


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, e.ordinal());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getInt(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getInt(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getInt(columnIndex));
    }

    private E convert(int status) {
            try {
                return this.enums[status];
            } catch (Exception var5) {
                throw new IllegalArgumentException("Cannot convert " + status + " to " + this.type.getSimpleName() + " by ordinal value.", var5);
            }
    }
}