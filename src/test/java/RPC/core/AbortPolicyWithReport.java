package RPC.core;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 当线程池接收线程被拒绝的时候执行的处理程序----线程池限制和队列满了时执行的策略
 * Created by caoqingyuan on 2017/10/27.
 */
public class AbortPolicyWithReport extends ThreadPoolExecutor.AbortPolicy {
    private final String threadName;
    public AbortPolicyWithReport(String threadName){
        this.threadName=threadName;
    }

    //TODO 当被拒绝的时候，接收这种异常执行的方法，必须总是抛出RejectedExecutionException
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e){
        //以指定的格式来格式化字符串
        String msg = String.format("RpcServer["
                        + " Thread Name: %s, Pool Size: %d (active: %d, core: %d, max: %d, largest: %d), Task: %d (completed: %d),"
                        + " Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s)]",
                threadName, e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(), e.getLargestPoolSize(),
                e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(), e.isTerminating());
        System.out.println(msg);
        throw  new RejectedExecutionException(msg);
    }
}
