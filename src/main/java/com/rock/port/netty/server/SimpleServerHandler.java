package com.rock.port.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 服务端请求处理handler--??
 * Created by caoqingyuan on 2017/10/26.
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    //ChannelHandlerContext作用：使通道处理器与通道管道进行交互
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws Exception{
        System.out.println("channelRead");
        // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
        ByteBuf result=(ByteBuf)msg;

        byte[] result1=new byte[result.readableBytes()];
        result.readBytes(result1);//把缓存中的数据转移到指定位置中
        //打印客户端信息
        System.out.println("client info:"+new String(result1));

        result.release();//释放缓存资源

        //向客户端发送消息
        String response="server ok";
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
