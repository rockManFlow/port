package RPC.core;

import RPC.model.MyMessageResponse;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 消息回调处理模块
 * Created by caoqingyuan on 2017/10/27.
 */
public class MessageCallBack {
    private MyMessageResponse response;
    private Lock lock = new ReentrantLock();
    private Condition finish = lock.newCondition();//??

    public MessageCallBack() {
    }

    public Object start() throws InterruptedException {
        try {
            lock.lock();
            //设定一下超时时间，rpc服务器太久没有相应的话，就默认返回空吧。
            finish.await(20*1000, TimeUnit.MILLISECONDS);
            System.out.println("callback await");
            if (this.response != null) {
                return this.response;
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    //有返回结果设置返回值
    public void over(MyMessageResponse reponse) {
        try {
            lock.lock();
            finish.signal();
            this.response = reponse;
        } finally {
            lock.unlock();
        }
    }
}
