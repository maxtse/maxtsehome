package myself.proxy.learn;

import myself.proxy.learn.impl.MyRemoteImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-11
 * Time: 下午5:28
 * To change this template use File | Settings | File Templates.
 */
public class RemoteRegistry {

    public static void main(String[] args) {
        try {
            MyRemote service = new MyRemoteImpl();//实现类
            LocateRegistry.createRegistry(1113);//开启一个端口
            //注册 第一个参数是客户端用来获取stub的名字
            Naming.rebind("rmi://127.0.0.1:1113/RemoteHello", service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
