package com.rock.port.zookeeper.clusterManage;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by caoqingyuan on 2017/12/25.
 */
public class Test implements Watcher {
    private ZooKeeper zk;
    private String groupNode = "cluster";

    @Override
    public void process(WatchedEvent watchedEvent) {
        try {
            System.out.println("process");
            Event.EventType type = watchedEvent.getType();
            String path = watchedEvent.getPath();
            System.out.println("事件类型：" + type.toString() + ",事件节点路径：" +path);
            create();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void createZookeeper() throws IOException, KeeperException, InterruptedException {
        zk = new ZooKeeper("192.168.31.118:2181", 5000, this);
        //查看要检测的服务器集群的根节点是否存在，如果不存在，则创建
        if (null == zk.exists("/" + groupNode, false)) {
            zk.create("/" + groupNode, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    private void create() throws KeeperException, InterruptedException {
        //是否注册监听 true：是
        List<String> children = zk.getChildren("/"+groupNode, true);
        for (String chName : children) {
            System.out.println("create:"+chName);
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        Test t=new Test();
        t.createZookeeper();

        Thread.sleep(100*1000);
    }
}
