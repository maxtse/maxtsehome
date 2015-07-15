package myself.proxy.learn.impl;

import myself.proxy.learn.MyRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-11
 * Time: 下午5:25
 * To change this template use File | Settings | File Templates.
 */
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {

    public MyRemoteImpl() throws RemoteException {};

    @Override
    public String sayHello() throws RemoteException {
        return "Server says , Hey";
    }
}
