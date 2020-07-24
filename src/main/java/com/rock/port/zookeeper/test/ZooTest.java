package com.rock.port.zookeeper.test;


import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * Created by caoqingyuan on 2017/12/16.
 */
public class ZooTest implements Watcher {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main1(String[] args) throws Exception {
        System.out.println("startccccc");
        String path = "/zk-book";
        zk = new ZooKeeper("127.0.0.1:2181", 5000, //
                new ZooTest());
        connectedSemaphore.await();
        System.out.println("endccccc");

        zk.exists(path, true);

        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.setData(path, "123".getBytes(), -1);

        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("success create znode: " + path + "/c1");

        zk.delete(path + "/c1", -1);
        zk.delete(path, -1);

        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event) {
        try {
            if (Event.KeeperState.SyncConnected == event.getState()) {
                if (Event.EventType.None == event.getType() && null == event.getPath()) {
                    connectedSemaphore.countDown();
                } else if (Event.EventType.NodeCreated == event.getType()) {
                    System.out.println("success create znode: " + event.getPath());
                    zk.exists(event.getPath(), true);
                } else if (Event.EventType.NodeDeleted == event.getType()) {
                    System.out.println("success delete znode: " + event.getPath());
                    zk.exists(event.getPath(), true);
                } else if (Event.EventType.NodeDataChanged == event.getType()) {
                    System.out.println("data changed of znode: " + event.getPath());
                    zk.exists(event.getPath(), true);
                }
            }
        } catch (Exception e) {
        }
    }



    public static void main(String[] args){
        String chi="server0000000010";
        String substring = chi.substring(chi.length() - 10);
        long fix = Long.parseLong(substring);
        System.out.println(fix);
    }
}
