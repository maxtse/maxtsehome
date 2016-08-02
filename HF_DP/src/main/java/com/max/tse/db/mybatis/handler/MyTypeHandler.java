package com.max.tse.db.mybatis.handler;

import com.max.tse.db.mybatis.enums.AgeType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-9
 * Time: 下午7:01
 * To change this template use File | Settings | File Templates.
 */
@MappedTypes(AgeType.class)
public class MyTypeHandler extends BaseTypeHandler<AgeType>{

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AgeType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.code);

    }

    @Override
    public AgeType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int ageTypeCode = rs.getInt(columnName);

        AgeType ageType = AgeType.fromCode(ageTypeCode);
        return ageType;
    }

    @Override
    public AgeType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int ageTypeCode = rs.getInt(columnIndex);

        AgeType ageType = AgeType.fromCode(ageTypeCode);
        return ageType;
    }

    @Override
    public AgeType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int ageTypeCode = cs.getInt(columnIndex);

        AgeType ageType = AgeType.fromCode(ageTypeCode);
        return ageType;
    }
}
