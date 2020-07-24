package com.rock.port.zookeeper.shareLock;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.*;

/**
 * TODO 使用zookeeper实现分布式共享锁
 * Created by caoqingyuan on 2017/12/25.
 */
public class MyLock implements Callable {
    private ZooKeeper zooKeeper;
    private String lockRootPath = "/Locks";
    private String lockName;
    private CountDownLatch count = new CountDownLatch(1);
    //zookeeper服务器中的锁路径
    private String lockPath;

    public MyLock(String nodeName) {
        try {
            zooKeeper = new ZooKeeper("192.168.31.118:2181", 50000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                }
            });
            if (zooKeeper.exists(lockRootPath, false) == null) {
                System.out.println("创建根节点");
                zooKeeper.create(lockRootPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            //创建本机节点
            createLocalLock(nodeName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    //创建自己的顺序锁标记
    private void createLocalLock(String lockName) {
        try {
            String localPath = lockRootPath + "/" + lockName;
            System.out.println("创建本机锁节点【" + localPath + "】");
            String st = zooKeeper.create(localPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            this.lockPath = st;
            this.lockName = st.substring((lockRootPath + "/").length());
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //获取本机创建的节点名
    public String getLockPath() {
        return this.lockPath;
    }

    //获取本次的连接实例对象
    public ZooKeeper getZooKeeper() {
        return this.zooKeeper;
    }

    //释放锁+并会关闭zookeeper连接实例
    public static boolean unlock(ZooKeeper zooKeeper, String lockPath) {
        try {
            zooKeeper.delete(lockPath, -1);
            zooKeeper.close();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean getLock(long timeOut) {
        return getLock();
    }

    private boolean getLock() {
        try {
            List<String> children = zooKeeper.getChildren(lockRootPath, false);
            TreeMap<Long, String> tree = new TreeMap<>();
            for (String chi : children) {
                long fix = Long.parseLong(chi.substring(chi.length() - 10));
                tree.put(fix, chi);
            }

            String s = tree.get(tree.firstKey());
            System.out.println("本机的lockname:" + lockName + "||获取的第一个锁名：" + s);
            if (s.equals(lockName)) {
                //是就获取到共享锁
                System.out.println("得到锁");
                count.countDown();//释放锁等待
                return true;
            } else {
                //对根节点进行监控
                System.out.println("对根节点进行监控");
                zooKeeper.getChildren(lockRootPath, new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        System.out.println("监控事件：" + watchedEvent.getType());
                        //写监控内容
                        if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                            //孩子节点改变
                            System.out.println("监控到孩子节点改变，再次进行锁的获取");
                            getLock();
                        }
                    }
                });
                //使线程阻塞，不继续往下执行，直到获取到锁
                System.out.println("线程开始阻塞");
                count.await();
                return true;
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("获取到false,有问题");
        return false;
    }

    @Override
    public Object call() throws Exception {
        return getLock();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        MyLock myLock = new MyLock("server");
        FutureTask result = new FutureTask(myLock);
        new Thread(result).start();
        boolean re1 = false;


        MyLock myLock2 = new MyLock("port222");
        FutureTask result2 = new FutureTask<>(myLock2);
        new Thread(result2).start();
        boolean re2 = false;

        int num = 2;
        while (true) {
            System.out.println("任务二：" + result2.isDone());
            if (result2.isDone()) {
                System.out.println("任务二状态：" + result.get());
                boolean bb2 = false;
                try {
                    bb2 = (boolean) result2.get(1, TimeUnit.SECONDS);
                } catch (TimeoutException t) {
                    System.out.println(t.getMessage());
                }
                if (bb2 && !re2) {
                    System.out.println("22获取到锁，开始执行任务");
                    Thread.sleep(2 * 1000);
                    System.out.println("22任务执行完成，释放锁");
                    MyLock.unlock(myLock2.getZooKeeper(), myLock2.getLockPath());
                    num = num - 1;
                    re2 = true;
                }
            }

            System.out.println("任务一：" + result.isDone());
            if (result.isDone() && !re1) {
                System.out.println("任务一状态：" + result.get());
                if ((boolean) result.get()) {
                    System.out.println("11获取到锁，开始执行任务");
                    Thread.sleep(3 * 1000);
                    System.out.println("11任务执行完成，释放锁");
                    MyLock.unlock(myLock.getZooKeeper(), myLock.getLockPath());
                    num = num - 1;
                    re1 = true;
                }
            }
            if (num == 0) {
                System.out.println("全部任务锁执行完毕，退出循环");
                break;
            }
            Thread.sleep(1 * 1000);
        }

        Thread.sleep(2 * 1000);
        System.out.println("主任务执行完毕，结束");
    }

}
