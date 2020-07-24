package RPC.impl;

import RPC.inter.MyRequestBody;
import RPC.model.MyMessageRequest;

/**
 * 代表不同的请求体
 * Created by caoqingyuan on 2017/10/30.
 */
public class MyRequestB implements MyRequestBody {
    @Override
    public Object createAndSendRequestBody() {
        MyMessageRequest request=new MyMessageRequest();
        request.setType("TYPE_B");
        request.setDesc("B类型的请求体");
        request.setContext("B body info...");
        return request;
    }
}
