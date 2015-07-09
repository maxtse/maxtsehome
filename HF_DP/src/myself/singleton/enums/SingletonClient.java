package myself.singleton.enums;


/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-1
 * Time: 下午10:34
 * To change this template use File | Settings | File Templates.
 */
public class SingletonClient {
    public static void main(String[] args) {
        Singleton.INSTANCE.process();
        Singleton.INSTANCE.log();
        Singleton.INSTANCE.log();
    }
}
