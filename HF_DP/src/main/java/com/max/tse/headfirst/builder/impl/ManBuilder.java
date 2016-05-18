package com.max.tse.headfirst.builder.impl;

import com.max.tse.headfirst.builder.BuilderInter;
import com.max.tse.headfirst.builder.po.Man;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-13
 * Time: 上午12:16
 * To change this template use File | Settings | File Templates.
 */
public class ManBuilder implements BuilderInter<Man> {

    @Override
    public Man build() {
        return Man.newBuilder().build();
    }
}
