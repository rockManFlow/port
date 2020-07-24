package RPC;

import RPC.core.MessageSendExecutor;
import RPC.core.MessageSendProxy;
import RPC.impl.MyRequestA;
import RPC.impl.MyRequestB;
import RPC.inter.MyRequestBody;
import RPC.model.MyMessageResponse;

import java.lang.reflect.Proxy;

/**
 * Created by caoqingyuan on 2017/10/27.
 */
public class ClientMain {
    public static void main(String[] args) {
        String address="127.0.0.1:18801";
        //执行客户端netty请求
        MessageSendExecutor sendExecutor=new MessageSendExecutor(address);
        //执行任务
        MyRequestA a=new MyRequestA();
        MyRequestBody myRequestBody=sendExecutor.execute(a);
        MyMessageResponse responseBodyA =(MyMessageResponse) myRequestBody.createAndSendRequestBody();
        System.out.println("A:"+responseBodyA);
        System.out.println("===========================");
        MyRequestB b=new MyRequestB();
        MyRequestBody myRequestBodyB=sendExecutor.execute(b);
        MyMessageResponse responseBodyB =(MyMessageResponse) myRequestBodyB.createAndSendRequestBody();
        System.out.println("B:"+responseBodyB);

        System.out.println("================");
        sendExecutor.stop();
        System.out.println("end");
    }

    public <T> T test(){
        //需要代理的类
        Object reqObject=new Object();
        return (T) Proxy.newProxyInstance(
                reqObject.getClass().getClassLoader(),
                reqObject.getClass().getInterfaces(),
                //handler新功能的处理器
                new MessageSendProxy<T>(reqObject)
        );
    }
}
