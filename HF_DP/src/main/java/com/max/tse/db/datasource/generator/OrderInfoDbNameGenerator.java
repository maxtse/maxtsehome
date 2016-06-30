package com.max.tse.db.datasource.generator;

import com.max.tse.common.context.AppContext;
import com.max.tse.common.utils.MD5Util;
import com.max.tse.db.datasource.generator.DbNameHashGenerator;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-20
 * Time: 下午2:57
 * To change this template use File | Settings | File Templates.
 */
public class OrderInfoDbNameGenerator extends DbNameHashGenerator {

    public OrderInfoDbNameGenerator(String tableNamePrefix, int tableSize) {
        super(tableNamePrefix, tableSize);
    }

    @Override
    public int hash() {
        String orderNo = AppContext.getOrderNo();
        return MD5Util.hashCode(orderNo).asInt();
    }


}
