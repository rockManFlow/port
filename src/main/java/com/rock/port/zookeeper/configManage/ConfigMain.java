package com.rock.port.zookeeper.configManage;

import org.apache.commons.io.IOUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 实现配置管理---当做修改者
 * Created by caoqingyuan on 2017/12/18.
 */
public class ConfigMain {
    private static ZooKeeper zooKeeper;
    private static String rootPath="/configuration";

    public ConfigMain(){
        try {
            zooKeeper = new ZooKeeper("192.168.31.118:2181", 50000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConfigMain(Watcher watcher){
        try {
            zooKeeper = new ZooKeeper("192.168.31.118:2181", 50000, watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ZooKeeper getZooKeeper(){
        return zooKeeper;
    }

    public ZooKeeper getZooKeeper(Watcher watcher){
        try {
            zooKeeper = new ZooKeeper("192.168.31.118:2181", 5000, watcher);
            return zooKeeper;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ConfigMain config=new ConfigMain();
        if(!config.exists(rootPath)) {
            //创建配置根节点
            config.createPersis(rootPath);
        }
        //创建存储子节点
        String childPath=rootPath+"/app";
        if(!config.exists(childPath)) {
            config.createPersis(childPath);
        }
        //获取配置文件信息
//        InputStream read=BaseConnector.class.getClassLoader().getResourceAsStream("node.txt");
        String st=new String(IOUtils.toByteArray(new FileInputStream("")));
        String[] split = st.split("\\r\\n");
        String context1=split[0];
        String context2=split[1];
        System.out.println(context1);
        config.updateConfig(childPath,context1);
        Thread.sleep(30*1000);
        System.out.println("第二个修改开始");
        config.updateConfig(childPath,context2);
        Thread.sleep(10*1000);
        System.out.println("end");
    }

    public void createPersis(String nodePath) throws KeeperException, InterruptedException, IOException {
        //创建zookeeper的连接实例
        //创建持久化节点
        zooKeeper.create(nodePath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    public void updateConfig(String nodePath,String configValue) throws KeeperException, InterruptedException {
        //第三个参数为版本，如果是-1，那会无视被修改的数据版本，直接改掉
        zooKeeper.setData(nodePath,configValue.getBytes(), -1);
    }

    public boolean exists(String path){
        try {
            Stat stat = zooKeeper.exists(path, true);
            if(stat!=null){
                return true;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
