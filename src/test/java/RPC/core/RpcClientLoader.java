package RPC.core;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * rpc服务器配置加载---单利，客户端就加载一次
 * Created by caoqingyuan on 2017/10/27.
 */
public class RpcClientLoader {
    private volatile static RpcClientLoader rpcClientLoader;
    private final static String DELIMITER = ":";

    //方法返回到Java虚拟机的可用的处理器数量
    //每个应用程序都有一个Runtime(运行时对象)---可以获取运行环境的一些参数
    //返回可用于Java虚拟机的处理器数量
    private final static int parallel = Runtime.getRuntime().availableProcessors() * 2;
    //netty nio线程池----事件监听组
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(parallel);
    //多线程的调用netty的handler
    private static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) RpcThreadPool.getExecutor(16, -1);
    private MessageSendHandler messageSendHandler = null;

    //等待Netty服务端链路建立通知信号
    private Lock lock = new ReentrantLock();
    private Condition signal = lock.newCondition();

    private RpcClientLoader() {
    }

    //并发双重锁定---这种方式实现单利较好
    public static RpcClientLoader getInstance() {
        if (rpcClientLoader == null) {
            synchronized (RpcClientLoader.class) {
                if (rpcClientLoader == null) {
                    rpcClientLoader = new RpcClientLoader();
                }
            }
        }
        return rpcClientLoader;
    }

    //serverAddress形式：ip:port
    public void load(String serverAddress) {
        //拆分ip和port
        String[] ipAddr = serverAddress.split(RpcClientLoader.DELIMITER);
        if (ipAddr.length == 2) {
            String host = ipAddr[0];
            int port = Integer.parseInt(ipAddr[1]);
            final InetSocketAddress remoteAddr = new InetSocketAddress(host, port);

            threadPoolExecutor.submit(new MessageSendInitializeTask(eventLoopGroup, remoteAddr, this));
        }
    }

    public void setMessageSendHandler(MessageSendHandler messageInHandler) {
        try {
            lock.lock();
            this.messageSendHandler = messageInHandler;
            //唤醒所有等待客户端RPC线程
            signal.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public MessageSendHandler getMessageSendHandler() throws InterruptedException {
        try {
            lock.lock();
            //Netty服务端链路没有建立完毕之前，先挂起等待
            if (messageSendHandler == null) {
                signal.await();
            }
            return messageSendHandler;
        } finally {
            lock.unlock();
        }
    }

    //显示的关闭连接的通道---如果不关闭会按照默认的条件进行关闭
    public void unLoad() {
        messageSendHandler.close();
        threadPoolExecutor.shutdown();
        eventLoopGroup.shutdownGracefully();
    }
}
