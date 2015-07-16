package myself.proxy.learn;

import java.rmi.Naming;
import java.rmi.NotBoundException;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-11
 * Time: 下午5:30
 * To change this template use File | Settings | File Templates.
 */
public class MyRemoteClient {
    public static void main(String[] args) {
        new MyRemoteClient().go();
    }
    public void go() {
        try {
            String[] list = Naming.list("rmi://127.0.0.1:1111");
            System.out.println(list[0]);
            MyRemote service = (MyRemote) Naming.lookup("rmi://127.0.0.1:1111/RemoteHello");
            String s = service.sayHello();
            System.out.print(s);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
