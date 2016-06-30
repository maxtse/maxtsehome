package com.max.tse.db.datasource.impl;

import com.max.tse.db.datasource.TseDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-15
 * Time: 下午12:10
 * To change this template use File | Settings | File Templates.
 * Note:委派
 */
public class DelegateDataSource implements TseDataSource {

    private TseDataSource delegate;

    public DelegateDataSource(TseDataSource delegate) {
        this.delegate = delegate;
    }


    @Override
    public Connection getConnection() throws SQLException {
        return delegate.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return delegate.getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return delegate.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        delegate.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        delegate.setLoginTimeout(seconds);

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return delegate.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return delegate.getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return delegate.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return delegate.isWrapperFor(iface);
    }

    @Override
    public void reInit(Properties newConfig, boolean shutDownNow) {
        delegate.reInit(newConfig, shutDownNow);
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }
}
