package com.rock.port.service;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by caoqingyuan on 2017/11/3.
 */
public class ThreadPoolService {

    public static Map<String,Executor> map=new Hashtable();
    public static Executor getPoolExecutor(String poolType){
        Executor poolExecutor=map.get(poolType);
        if(poolExecutor==null){
            synchronized (ThreadPoolService.class) {
                if (poolExecutor == null) {
                    poolExecutor = new ThreadPoolExecutor(10, 10, 5, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
                    map.put(poolType,poolExecutor);
                }
            }
        }
        return poolExecutor;
    }
}
