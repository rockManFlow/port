package RPC.core;

import RPC.model.MyMessageRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 代理类--发送消息代理类
 * Created by caoqingyuan on 2017/10/27.
 */
public class MessageSendProxy<T> implements InvocationHandler {
    private Object object;
    public MessageSendProxy(Object object){
        this.object=object;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //创建请求体
        MyMessageRequest request = (MyMessageRequest)method.invoke(object, args);
        request.setMessageId(UUID.randomUUID().toString().replaceAll("-",""));

        MessageSendHandler handler = RpcClientLoader.getInstance().getMessageSendHandler();
        MessageCallBack callBack = handler.sendRequest(request);
        return callBack.start();
    }
}
