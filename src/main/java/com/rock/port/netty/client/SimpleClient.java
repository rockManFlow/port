package com.rock.port.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by caoqingyuan on 2017/10/26.
 */
public class SimpleClient {
    public void connect(String host, int port) throws Exception {
        //用于监听server端的消息或io事件
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            //设置一个选择器的活动状态
            b.option(ChannelOption.TCP_NODELAY, true);
//            b.option(ChannelOption.SO_KEEPALIVE, true);//客户端对象是单利的  //一直保持活着
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new SimpleClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync();

            System.out.println("finished connect");
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
            System.out.println("等待连接关闭");
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        SimpleClient client=new SimpleClient();
        client.connect("127.0.0.1", 9999);
    }
}
