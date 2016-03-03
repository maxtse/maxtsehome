package com.max.tse.javaSelf.abstractInterface;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-2-29
 * Time: 下午4:54
 * To change this template use File | Settings | File Templates.
 * Note:报警门 有门的共有属性和报警动作
 */
public class AlarmDoor extends Door implements Alarm{

    public AlarmDoor() {
        super();
    }

    public AlarmDoor(String name) {
        super(name);
    }


    @Override
    public void alarm() {

    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }
}
