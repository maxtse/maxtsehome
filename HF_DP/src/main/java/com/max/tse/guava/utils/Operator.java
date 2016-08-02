package com.max.tse.guava.utils;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-8-5
 * Time: 上午12:39
 * To change this template use File | Settings | File Templates.
 */
public enum Operator {    /**
 * 用户
 */
    USER

    {
        @Override
        public String getName () {
        return name == null ? "用户" : "用户" + name;
    }

    }

    ,

    /**
     * 系统
     */
    SYSTEM

    {
        @Override
        public String getName () {
        return "System";
    }
    }

    ,

    /**
     * 代理商管理员
     */
    ADMIN

    {
        @Override
        public String getName () {
        return name == null ? "代理商操作员" : "代理商操作员" + name;
    }
    }

    ;

    public String name;

    public abstract String getName();

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name() + this.getName();
    }
}