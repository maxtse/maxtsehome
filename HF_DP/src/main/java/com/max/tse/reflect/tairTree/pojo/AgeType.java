package com.max.tse.reflect.tairTree.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-12
 * Time: 下午6:40
 * To change this template use File | Settings | File Templates.
 */
public enum AgeType {

    audlt(1, "成人");
    AgeType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final int code;

    private final String desc;
}
