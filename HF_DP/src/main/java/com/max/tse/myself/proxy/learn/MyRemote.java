package com.max.tse.myself.proxy.learn;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-11
 * Time: 下午5:23
 * To change this template use File | Settings | File Templates.
 */
public interface MyRemote extends Remote {
    //返回值必须是sdfsd可序列化的
    public String sayHello() throws RemoteException;
}
