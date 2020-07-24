package com.rock.port.zookeeper.shareLock;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by caoqingyuan on 2017/12/25.
 */
public class DistributedLock {
    public static void main(String[] args){
        try {
            ZooKeeper zooKeeper = new ZooKeeper("192.168.31.118:2181", 5000,null);
            if(zooKeeper.exists("/test",false)==null) {
                zooKeeper.create("/test", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }


            Watcher watcher=new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("监控");
                    System.out.println(watchedEvent.getType());
                    try {
                        zooKeeper.exists("/test",true);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            //
            System.out.println("监控111");
            zooKeeper.exists("/test",watcher);

            System.out.println("chuangjian app1");
            zooKeeper.create("/test/app1",null,ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            Thread.sleep(13*1000);

            System.out.println("delete app1");
            zooKeeper.delete("/test/app1",-1);

            Thread.sleep(Long.MAX_VALUE);
        } catch (IOException i) {
            i.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
