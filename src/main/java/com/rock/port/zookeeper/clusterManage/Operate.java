package com.rock.port.zookeeper.clusterManage;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by caoqingyuan on 2017/12/20.
 */
public class Operate {
    public static void main(String[] args){
        try {
            ZooKeeper zooKeeper = new ZooKeeper("192.168.31.118:2181", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                }
            });
            zooKeeper.create("/cluster/hhhh","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            Thread.sleep(10*1000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
