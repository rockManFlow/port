package com.rock.port.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by caoqingyuan on 2017/10/26.
 */
public class SimpleServer {
    private int port;
    public SimpleServer(int port){
        this.port=port;
    }

    public void run(){
        //EventLoopGroup是用来处理io操作的多线程循环器--相当于io事件监听组

        //用来监听服务器端的io事件或发送的消息
        EventLoopGroup inputGroup=new NioEventLoopGroup();
        //用来监听client端的io事件或消息
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            //启动nio服务的辅助启动类
            ServerBootstrap bootstrap=new ServerBootstrap();
            //设置：服务器端监听器，client端监听器
            bootstrap.group(inputGroup,workerGroup)
                    //用于创建指定通道实体--NioServerSocketChannel基于选择器实现新连接
                    .channel(NioServerSocketChannel.class)
                    //用于处理客户端channel的请求----设置server处理器
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        //初始化处理
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //返回这个指定的通道管道  在这个管道最后插入通道处理器
                           socketChannel.pipeline().addLast(new SimpleServerHandler());
                        }
                    })
                    //用于设置通道选择的规则
                    .option(ChannelOption.SO_BACKLOG, 128)//最大保存的数量
                    .option(ChannelOption.TCP_NODELAY,true);//通道选择不推迟
            //创建新的channel并 绑定端口，开始接收进来的连接
            //ChannelFuture==所有的netty异步io请求都会立即返回这个实例，由这个实例去查询是否处理完成
            ChannelFuture f = bootstrap.bind(port).sync();//sync会一直等待直到返回一个最终的结果
            // 等待服务器 socket 关闭 。
            //channel()---获取一个与当前对象相关的channel
            //等待通道关闭之后也关闭这个对象
            System.out.println("channel future");
            f.channel().closeFuture().sync();//观察者监听
            System.out.println("close future");
        }catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //柔和的被关闭--当前通道监听组中没有待处理的任务，经过一个静默期就会自动关闭。有任务不关闭再次开始
            inputGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        new SimpleServer(9999).run();
    }
}
