package com.rock.port.zookeeper;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by caoqingyuan on 2017/12/15.
 */
public class CreateNodeZookeeper {
    public static void main1(String[] args) throws IOException, KeeperException, InterruptedException {
        //创建zookeeper的连接实例
        ZooKeeper zookeeper=new ZooKeeper("192.168.31.118:2181", 5000, null);
        String nodePath="/test";//节点路径 ---创建 /service/mysql-1 需要首先，创建 /service
        String nodeValue="蛋疼";//节点路径对应的值
        ArrayList<ACL> acl= ZooDefs.Ids.OPEN_ACL_UNSAFE;//acl访问权限
        CreateMode nodeType= CreateMode.EPHEMERAL;//节点类型

        if(zookeeper.exists(nodePath,false)!=null){
            zookeeper.delete(nodePath,-1);
//            zookeeper.create(nodePath,null,ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        }

//        zookeeper.create("/service", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("新建节点："+nodePath);
        zookeeper.create(nodePath,nodeValue.getBytes(),acl,nodeType);//创建临时节点
        Thread.sleep(10*1000);
        //改变指定节点中的数据
        System.out.println("改变指定节点下的数据");
        zookeeper.setData(nodePath,"hehehe".getBytes(),-1);
        Thread.sleep(10*1000);

        //
        System.out.println("再次新建节点："+nodePath+"agian");
        zookeeper.create(nodePath+"agian",nodeValue.getBytes(),acl,nodeType);//创建临时节点
        Thread.sleep(10*1000);
        //zookeeper客户端和zookeeper服务器端的连接

        zookeeper.close();
    }

    public static void main(String[] args){
        try {
            ZooKeeper zooKeeper = new ZooKeeper("192.168.31.118:2181", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println(watchedEvent.getType());
                }
            });

            Watcher watcher=new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("监控");
                    System.out.println(watchedEvent.getType());
                    try {
                        zooKeeper.getChildren("/test",true);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            zooKeeper.getChildren("/test",watcher);

            Thread.sleep(100*1000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
