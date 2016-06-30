package com.max.tse.db.datasource;

import javax.sql.DataSource;
import java.io.Closeable;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-19
 * Time: 下午4:11
 * To change this template use File | Settings | File Templates.
 */
public interface TseDataSource extends DataSource, Closeable{

    public void reInit(Properties newConfig, boolean shutDownNow);


}
