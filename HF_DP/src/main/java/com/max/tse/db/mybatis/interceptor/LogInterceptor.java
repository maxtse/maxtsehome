package com.max.tse.db.mybatis.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.Alias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-9
 * Time: 下午5:59
 * To change this template use File | Settings | File Templates.
 * MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler
 */
@Alias("logInterceptor")
@Intercepts(//这个注解指明了要在哪些类上的那些方法插入插件
        {@Signature(type= Executor.class,
                method="query",
                args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
public class LogInterceptor implements Interceptor{

    private Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    /**
     * Invocation类保存了目标类 和方法 以及参数
     *找到拦截类 才会执行这个方法 否则执行目标类的方法
     * 大体思路可以理清了 Plugin是个InvocationHandler
     * 最终生成代理，然后对方法的调用都转移到Plugin.invoke手上
     *
     * */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            //执行目标类的方法
            return invocation.proceed();
        }finally {

            Object[] args = invocation.getArgs();
            if (args != null && args[0] instanceof MappedStatement) {
                MappedStatement statement = (MappedStatement) args[0];
                Object paramter = args[1];
                String sql = statement.getBoundSql(paramter).getSql();
                logger.info("sql={}", sql);

            }

        }

    }

    @Override
    public Object plugin(Object target) {
        //Plugin.wrap（target,this)是获得一个代理对象
        //是层层代理下来的这里我们要找到最原始的那个代理对象 这个类里指的是Executor
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
