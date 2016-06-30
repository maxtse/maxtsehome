package com.max.tse.db.datasource.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-19
 * Time: 下午4:24
 * To change this template use File | Settings | File Templates.
 * Note:分库的一种方式：hash
 *
 */
public abstract class DbNameHashGenerator implements DbNameGenerator {

    private static final Logger logger = LoggerFactory.getLogger(DbNameHashGenerator.class);

    private String tableNamePrefix;

    private int tableSize;

    public DbNameHashGenerator(String tableNamePrefix, int tableSize) {
        this.tableNamePrefix = tableNamePrefix;
        this.tableSize = tableSize;
    }

    @Override
    public String generator() {

        int suffix = hash();
        int index = suffix % tableSize;

        String result = "";
        try {
            if (index < 10) {
                return result = tableNamePrefix + "0" + index;
            }
            return result = tableNamePrefix + index;
        } finally {
            logger.info("use table: {}",result);
        }
    }

    public abstract int hash();

}
