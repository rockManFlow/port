package com.rock.port.zookeeper.configManage;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * 实现统一的配置管理
 * Created by caoqingyuan on 2017/12/19.
 */
public class ClientSever implements Runnable {
    private static ZooKeeper zooKeeper;
    private String serverName = "默认名";

    public ClientSever() {
        try {
            zooKeeper = new ZooKeeper("192.168.31.118:2181", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                }
            });
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public ClientSever(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public void run() {
        MyWatchedEvent watcher = new MyWatchedEvent(serverName);
        watcher.setMonitorPath("/configuration/app");

        System.out.println("[" + serverName + "] start monitor znode....");
        //首先启动各个事件的监控功能
        watcher.monitorExists();
        watcher.monitorNodeNum();
//                Thread.sleep(3*1000);
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ClientSever("监控者A")).start();
//        new Thread(new ClientSever("监控者B")).start();
    }
}
