package com.rock.port.zookeeper.configManage;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by caoqingyuan on 2017/12/16.
 */
public class MyWatchedEvent implements Watcher {
    private static String configPath = "/configuration";

    private ZooKeeper zooKeeper;
    private String monitorPath;
    private String serverName;
    public MyWatchedEvent(String serverName){
        try {
            zooKeeper = new ZooKeeper("192.168.31.118:2181", 5000,this);
        } catch (IOException i) {
            i.printStackTrace();
        }
        this.serverName=serverName;
    }

    public void setMonitorPath(String monitorPath) {
        this.monitorPath = monitorPath;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        //获取事件类型
        Event.EventType type = watchedEvent.getType();
        System.out.println("事件类型：" + type.toString() + ",事件节点路径：" + watchedEvent.getPath());
        if(type==Event.EventType.NodeDeleted){
            monitorExists();
        }
        if(type==Event.EventType.NodeCreated){
            //孩子节点创建完成之后，进行孩子节点数据的监控
            monitorData();
            monitorExists();
        }
        if (type==Event.EventType.NodeDataChanged) {//事件类型为，节点对应的数据 发生变化
            monitorData();
        }
        if (type==Event.EventType.NodeChildrenChanged) {//事件类型为，路径 /service 下的节点发生变化，比如 添加节点，删除节点。
            monitorNodeNum();
        }
    }

    //节点状态的监控
    public void monitorExists(){
        //NodeDeleted  NodeCreated
        System.out.println("exists oper");
        try {
            //再次进行注册监控
            zooKeeper.exists(monitorPath,true);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //数据的监控
    public void monitorData() {
        System.out.println("monitorData start【"+serverName+"】");
        try {
            if(zooKeeper.exists(monitorPath,false)!=null) {
                byte[] data = zooKeeper.getData(monitorPath, true, null);
                System.out.println("节点数据状态发生改变：" + monitorPath + "|数据：" + new String(data, "utf-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //孩子节点的监控
    public void monitorNodeNum() {
        System.out.println("monitorNodeNum start");
        try {
            //获取指定节点下的子节点名称
            List<String> subList = zooKeeper.getChildren(configPath, true);
            for (int i = 0, size = subList.size(); i < size; i++) {
                String subNode = subList.get(i);
                //监控 /serverList 下面的 节点 对应 的 数据 是否发生变化，这里使用的是在创建 ZooKeeper 实例时指定的 watcher。
                System.out.println("孩子节点信息："+subNode);
                //获取子节点下的数据
//                byte[] data = zooKeeper.getData(configPath + "/" + subNode, true, null);
//                if (data != null && data.length > 0) {
//                    //输出 节点对应的数据
//                    System.out.println("节点数据: " + configPath + "/" + subNode + "=" + new String(data, "utf-8"));
//                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
