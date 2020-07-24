package RPC.core;

import RPC.model.MyMessageRequest;
import RPC.model.MyMessageResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * 服务器消息线程处理消息并生成对应的响应消息
 * Created by caoqingyuan on 2017/10/27.
 */
public class MessageRecvInitializeTask implements Runnable {

    private MyMessageRequest request = null;
    private MyMessageResponse response = null;
    private ChannelHandlerContext ctx = null;

    public MyMessageResponse getResponse() {
        return response;
    }

    public MyMessageRequest getRequest() {
        return request;
    }

    public void setRequest(MyMessageRequest request) {
        this.request = request;
    }

    MessageRecvInitializeTask(MyMessageRequest request, MyMessageResponse response, ChannelHandlerContext ctx) {
        this.request = request;
        this.response = response;
        this.ctx = ctx;
    }
    public void run() {
        response.setMessageId(request.getMessageId());
        try {
            reflect(request);
        } catch (Throwable t) {
            t.printStackTrace();
            System.err.printf("RPC Server invoke error!\n");
        }

        //发送响应报文，并监听响应是否完成
        ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.println("RPC Server Send message-id respone:" + request.getMessageId());
            }
        });
    }

    private void reflect(MyMessageRequest request) throws Throwable {
        String type = request.getType();
        if(type.contains("A")){
            response.setRepsCode("A"+"|OK");
            response.setDesc("A finish");
        }if(type.contains("B")){
            response.setRepsCode("B"+"|OK");
            response.setDesc("B finish");
        }
        String context = request.getContext();
        System.out.println("context:"+context);
        String desc=request.getDesc();
        System.out.println("desc:"+desc);
    }
}
