package zookeeper;

import com.google.common.collect.Lists;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-7-16
 * Time: 下午10:37
 * To change this template use File | Settings | File Templates.
 * start zookeeper
 */
public class FirstZookeeper {
    public static void main(String[] args) throws Exception{
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 500000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getPath());
            }
        });
        List<ACL> aclList = Lists.newArrayList();
        Id id = new Id("world", "anyone");
        ACL acl = new ACL(ZooDefs.Perms.ALL, id);
        aclList.add(acl);
        zooKeeper.create("/root/childOne", "childOne".getBytes(), aclList, CreateMode.PERSISTENT);

        List<String> result = zooKeeper.getChildren("/root", true);
        for (String string : result) {
            System.out.println(string);
        }
        System.out.println(zooKeeper.getData("/root/childOne", true, null).toString());
        zooKeeper.close();
    }
}
