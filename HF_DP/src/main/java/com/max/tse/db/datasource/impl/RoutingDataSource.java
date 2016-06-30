package com.max.tse.db.datasource.impl;

import com.google.common.collect.Maps;
import com.max.tse.db.datasource.TseDataSource;
import com.max.tse.db.datasource.factory.TseDataSourceFactory;
import com.max.tse.db.datasource.generator.RoutingKeyGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-26
 * Time: 下午2:23
 * To change this template use File | Settings | File Templates.
 */
public class RoutingDataSource implements TseDataSource {

    private TseDataSourceFactory  dataSourceFactory;

    private RoutingKeyGenerator routingKeyGenerator;

    private ConcurrentHashMap<String, TseDataSource> dataSources = new ConcurrentHashMap<String, TseDataSource>();


    public RoutingDataSource (TseDataSourceFactory dataSourceFactory, RoutingKeyGenerator keyGenerator) {
        this.dataSourceFactory = dataSourceFactory;
        this.routingKeyGenerator = keyGenerator;
    }
    @Override
    public void reInit(Properties newConfig, boolean shutDownNow) {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    public TseDataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    public void setDataSourceFactory(TseDataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    public RoutingKeyGenerator getRoutingKeyGenerator() {
        return routingKeyGenerator;
    }

    public void setRoutingKeyGenerator(RoutingKeyGenerator routingKeyGenerator) {
        this.routingKeyGenerator = routingKeyGenerator;
    }
}
