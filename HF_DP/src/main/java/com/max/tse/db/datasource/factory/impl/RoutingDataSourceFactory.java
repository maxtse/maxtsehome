package com.max.tse.db.datasource.factory.impl;

import com.max.tse.db.datasource.TseDataSource;
import com.max.tse.db.datasource.factory.TseDataSourceFactory;
import com.max.tse.db.datasource.generator.RoutingKeyGenerator;
import com.max.tse.db.datasource.impl.RoutingDataSource;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-26
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
public class RoutingDataSourceFactory extends DelegateDataSourceFactory{

    private RoutingKeyGenerator routingKeyGenerator;

    public RoutingDataSourceFactory(TseDataSourceFactory delegate, RoutingKeyGenerator routingKeyGenerator) {
        super(delegate);
        this.routingKeyGenerator = routingKeyGenerator;
    }

    @Override
    public TseDataSource create(Properties config) {
        RoutingDataSource dataSource = new RoutingDataSource(this.getDelegate(), this.routingKeyGenerator);
        dataSource.reInit(config, true);
        return dataSource;
    }
}
