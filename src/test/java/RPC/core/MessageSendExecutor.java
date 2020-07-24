package RPC.core;

import java.lang.reflect.Proxy;

/**
 *
 * Created by caoqingyuan on 2017/10/27.
 */
public class MessageSendExecutor {
    private RpcClientLoader loader= RpcClientLoader.getInstance();

    public MessageSendExecutor(String serverAddress){
        //相同的jvm中到相同的server只能连接一次
        loader.load(serverAddress);
    }

    public void stop() {
        loader.unLoad();
    }

    public static <T> T execute(Object reqObject) {
        return (T) Proxy.newProxyInstance(
                reqObject.getClass().getClassLoader(),
                reqObject.getClass().getInterfaces(),
                //handler新功能的处理器
                new MessageSendProxy<T>(reqObject)
        );
    }
}
