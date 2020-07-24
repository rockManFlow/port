package RPC.core;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 服务器执行模块
 * Created by caoqingyuan on 2017/10/27.
 */
public class MessageRecvExecutor{
    private static ThreadPoolExecutor threadPoolExecutor;

    public static void submit(Runnable task) {
        if (threadPoolExecutor == null) {
            synchronized (MessageRecvExecutor.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = (ThreadPoolExecutor) RpcThreadPool.getExecutor(16, -1);
                }
            }
        }
        threadPoolExecutor.submit(task);
    }
}
