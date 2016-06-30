package com.max.tse.db.datasource.factory;

import com.max.tse.db.datasource.TseDataSource;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-26
 * Time: 下午12:26
 * To change this template use File | Settings | File Templates.
 * Note：dataSourceFactory
 */
public interface TseDataSourceFactory {

    public TseDataSource create(Properties config);
}
