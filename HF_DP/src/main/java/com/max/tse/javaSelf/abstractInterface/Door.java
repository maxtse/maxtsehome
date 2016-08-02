package com.max.tse.javaSelf.abstractInterface;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-2-29
 * Time: 下午4:52
 * To change this template use File | Settings | File Templates.
 * Note:门的抽象
 */
public abstract class Door {
    private String name;

    public Door() {

    }

    public Door(String name) {
        this.name = name;
    }

    public abstract void open();
    public abstract void close();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
