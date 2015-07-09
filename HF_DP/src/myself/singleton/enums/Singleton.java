package myself.singleton.enums;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-1
 * Time: 下午10:23
 * To change this template use File | Settings | File Templates.
 */
public enum Singleton implements ActionService{
    INSTANCE{
        @Override
        public void process() {
            System.out.println("process:" + this.name());
        }

        @Override
        public void log() {
            System.out.println("log:" + this.name());

        }
    };



}
