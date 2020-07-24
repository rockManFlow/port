package RPC.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by caoqingyuan on 2017/10/27.
 */
public class NamedThreadFactory implements ThreadFactory {
    private static final AtomicInteger threadNumber=new AtomicInteger(1);

    private final AtomicInteger mthreadNum=new AtomicInteger(1);

    private final String prefix;

    private final boolean demoThread;

    private final ThreadGroup threadGroup;

    public NamedThreadFactory() {
        this("rpcserver-threadpool-" + threadNumber.getAndIncrement(), false);
    }

    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public NamedThreadFactory(String prefix, boolean daemo) {
        this.prefix = prefix + "-thread-";
        demoThread = daemo;
        SecurityManager s = System.getSecurityManager();
        threadGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    public ThreadGroup getThreadGroup() {
        return threadGroup;
    }
    @Override
    public Thread newThread(Runnable r) {
        String name = prefix + mthreadNum.getAndIncrement();
        Thread ret = new Thread(threadGroup, r, name, 0);
        //是否将此线程标记为守护线程？？
        ret.setDaemon(demoThread);
        return ret;
    }
}
