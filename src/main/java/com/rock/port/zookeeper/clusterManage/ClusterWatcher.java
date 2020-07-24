package com.rock.port.zookeeper.clusterManage;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 集群管理事件监听者
 * Created by caoqingyuan on 2017/12/20.
 */
public class ClusterWatcher implements Watcher {
    private CountDownLatch lock=new CountDownLatch(1);
    private ZooKeeper zooKeeper;
    private String path="/cluster";
    public ClusterWatcher(){

    }

    public void createZookeeper(){
        try {
            zooKeeper = new ZooKeeper("192.168.31.118:2181", 5000,this);
            lock.await();
            if(zooKeeper.exists(path,false)==null){
                System.out.println("创建持久化节点，为什么之前创建的消失了");
                zooKeeper.create(path,null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("process");
        if(watchedEvent.getState()== Event.KeeperState.SyncConnected){
            lock.countDown();
        }
        Event.EventType type = watchedEvent.getType();
        String path = watchedEvent.getPath();
        System.out.println("事件类型：" + type.toString() + ",事件节点路径：" +path);
        try {
            if (type.toString().equals(Event.EventType.None)) {
                System.out.println("exite unit");
                zooKeeper.exists(path, true);//监听节点是否存在
            }else if(type.toString().equals(Event.EventType.NodeChildrenChanged)||type.toString().equals(Event.EventType.NodeDeleted)){
                monitor(zooKeeper);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
//        if(type.toString().equals(Event.EventType.NodeChildrenChanged)){
            //孩子节点改变执行动作
//            monitor(zooKeeper);
//        }
    }

    private void monitor(ZooKeeper zooKeeper){
        try {
            //每次监控都需要重新进行注册监听
            List<String> children = zooKeeper.getChildren(path, true);
            for(String subNode:children){
                byte[] data = zooKeeper.getData(path + "/" + subNode, true, new Stat());
                System.out.println("节点数据: " + path + "/" + subNode + "=" + new String(data, "utf-8"));
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ClusterWatcher().createZookeeper();
        Thread.sleep(140*1000);
    }
}
