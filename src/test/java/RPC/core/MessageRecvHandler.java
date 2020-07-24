package RPC.core;

import RPC.model.MyMessageRequest;
import RPC.model.MyMessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务器具体消息处理器
 * Created by caoqingyuan on 2017/10/27.
 */
public class MessageRecvHandler extends ChannelInboundHandlerAdapter {

    public MessageRecvHandler() {
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MyMessageRequest request = (MyMessageRequest) msg;
        MyMessageResponse response = new MyMessageResponse();
        MessageRecvInitializeTask recvTask = new MessageRecvInitializeTask(request, response, ctx);
        //不要阻塞nio线程，复杂的业务逻辑丢给专门的线程池
        MessageRecvExecutor.submit(recvTask);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //网络有异常要关闭通道
        ctx.close();
    }
}
