package com.rock.port.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by caoqingyuan on 2017/12/16.
 */
public class ZookeeperClient {
    //监控 路径
    private static String monitorPath = "/service";

    private static Stat stat = new Stat();

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException {
        try {
            //创建zookeeper实例---目标服务器地址和端口(可不可以认为是zookeeper所在的服务器地址) session的超时时间 节点变化的回调方法
            zooKeeper = new ZooKeeper("192.168.31.118:2181", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    //获取事件类型
                    Event.EventType type = watchedEvent.getType();
                    System.out.println("事件类型：" + type.toString() + ",事件节点路径：" + watchedEvent.getPath());
                    if (type.equals(Event.EventType.NodeDataChanged)) {//事件类型为，节点对应的数据 发生变化
                        monitor(zooKeeper);
                    }
                    if (type.equals(Event.EventType.NodeChildrenChanged)) {//事件类型为，路径 /service 下的节点发生变化，比如 添加节点，删除节点。
                        monitor(zooKeeper);
                    }
                }
            });

            monitor(zooKeeper);

            //保证 main 线程 不会 死掉
            Thread.sleep(Long.MAX_VALUE);
        }catch (InterruptedException i){
            i.printStackTrace();
        }
    }

    //zookeeper监听
    public static void monitor(ZooKeeper zooKeeper){
        try {
            //监控 /serverList 下面 是否 创建了新节点 或者删除了新节点，
            // 这里使用的是在创建 ZooKeeper 实例时指定的 watcher。
            //也可以指定特定的 watcher 例如 zk.getChildren(String path, Watcher watcher);
            //注意：每次调用 Watcher 的 process 方法，如果想要对相应的 目录或者目录对应的数据进行监听，都需要重新注册，因为注册一次，只能调用一次 Watcher 的 process 方法

            //获取指定节点下的子节点名称
            List<String> subList = zooKeeper.getChildren(monitorPath, true);
            for (int i=0,size=subList.size(); i<size; i++) {
                String subNode = subList.get(i);
                //监控 /serverList 下面的 节点 对应 的 数据 是否发生变化，这里使用的是在创建 ZooKeeper 实例时指定的 watcher。

                //获取子节点下的数据
                byte[] data = zooKeeper.getData(monitorPath + "/" + subNode, true, stat);
                if(data != null && data.length > 0) {
                    //输出 节点对应的数据
                    System.out.println("节点数据: " + monitorPath + "/" + subNode + "=" + new String(data, "utf-8"));
                }

                //修改节点/root/childone下的数据，第二个为新数据 第三个参数为版本，如果是-1，那会无视被修改的数据版本，直接改掉
                //zk.SetData("/root/childone", "childonemodify".GetBytes(), -1);
                //删除/root/childone这个节点，第二个参数为版本，－1的话直接删除，无视版本
                //zk.Delete("/root/childone", -1);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
