package com.rock.port.zookeeper.clusterManage;

import org.apache.log4j.Logger;
import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by caoqingyuan on 2017/12/20.
 */
public class Client implements Runnable {
    private static final Logger logger = Logger.getLogger(Client.class);
    private String path = "/cluster";
    private ZooKeeper zooKeeper;
    private String nodeName;

    public Client(String nodeName) {
        this.nodeName = nodeName;
        try {
            zooKeeper = new ZooKeeper("192.168.31.118:2181", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Watcher watcher = new ClusterWatcher();
        try {
            if (zooKeeper.exists(path + "/" + nodeName, true) == null) {
                logger.info("创建临时节点："+nodeName);
                //创建临时节点
                zooKeeper.create(path + "/" + nodeName, nodeName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                zooKeeper.exists(path, watcher);
//                logger.info("节点名：" + nodeName);
                Thread.sleep(2 * 1000);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Client("app1")).start();
        System.out.println("app1 start");
        Thread.sleep(8*1000);
        System.out.println("app2 start");
        new Thread(new Client("app2")).start();
    }
}
