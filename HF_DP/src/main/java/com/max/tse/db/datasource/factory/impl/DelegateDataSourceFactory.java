package com.max.tse.db.datasource.factory.impl;

import com.max.tse.db.datasource.TseDataSource;
import com.max.tse.db.datasource.factory.TseDataSourceFactory;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-26
 * Time: 下午12:27
 * To change this template use File | Settings | File Templates.
 * Note：委派
 */
public class DelegateDataSourceFactory implements TseDataSourceFactory{

    private TseDataSourceFactory delegate;

    public DelegateDataSourceFactory(TseDataSourceFactory delegate) {
        this.delegate = delegate;
    }

    @Override
    public TseDataSource create(Properties config) {
        return delegate.create(config);
    }

    public TseDataSourceFactory getDelegate() {
        return delegate;
    }

    public void setDelegate(TseDataSourceFactory delegate) {
        this.delegate = delegate;
    }
}
