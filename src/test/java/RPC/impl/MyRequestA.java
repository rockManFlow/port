package RPC.impl;

import RPC.inter.MyRequestBody;
import RPC.model.MyMessageRequest;

/**
 * Created by caoqingyuan on 2017/10/30.
 */
public class MyRequestA implements MyRequestBody {
    @Override
    public Object createAndSendRequestBody() {
        MyMessageRequest request=new MyMessageRequest();
        //唯一定义一笔请求
        request.setType("TYPE_A");
        request.setDesc("A类型的请求体");
        request.setContext("A requet body info");
        return request;
    }
}
