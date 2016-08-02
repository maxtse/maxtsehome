package com.max.tse.db.datasource;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-19
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 * Note:分库的一种方式：hash
 *
 */
public abstract class RoutingKeyHashGenerator implements DbNameGenerator{

    private String tableNamePrefix;

    private int tableSize;

    public RoutingKeyHashGenerator (String tableNamePrefix, int tableSize) {
        this.tableNamePrefix = tableNamePrefix;
        this.tableSize = tableSize;
    }



    public abstract int hash(String keyWord);
}
