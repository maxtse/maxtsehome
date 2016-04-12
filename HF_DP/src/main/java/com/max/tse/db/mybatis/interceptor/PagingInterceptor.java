package com.max.tse.db.mybatis.interceptor;

import com.max.tse.reflect.ReflectUtil;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.Alias;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-11
 * Time: 上午10:00
 * To change this template use File | Settings | File Templates.
 */
@Alias("pagingInterceptor")
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PagingInterceptor implements Interceptor{

    private static final String  SQL_END_LETTER = ";";
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (target instanceof RoutingStatementHandler) {

            BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtil.getDeclaredFieldValue(target, "delegate");

            RowBounds rowBounds = (RowBounds) ReflectUtil.getDeclaredFieldValue(delegate, "rowBounds");
            if (rowBounds.getLimit() > 0 && rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT) {

                BoundSql boundSql = delegate.getBoundSql();
                String sql = boundSql.getSql();
                sql = getLimitString(sql, rowBounds.getOffset(), rowBounds.getLimit());
                ReflectUtil.setDeclaredFieldValue(boundSql, "sql", sql);
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private String getLimitString(String sql, int offSet, int limit) {
        String _sql = sql;
        if (sql.endsWith(SQL_END_LETTER)) {
            _sql = sql.substring(0, sql.length() - 1);
        }
        return buildLimitSql(new StringBuilder(_sql), offSet, limit).append(SQL_END_LETTER).toString();

    }

    private StringBuilder buildLimitSql(StringBuilder stringBuilder, int offSet, int limit) {
        stringBuilder.append(" limit ").append(limit);
        if (offSet > 0) {
            stringBuilder.append(" offset ").append(offSet);
        }
        return stringBuilder;
    }


}
