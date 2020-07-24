package com.rock.port.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * Created by caoqingyuan on 2018/1/4.
 */
public class ServerMain {
    public static void main(String[] args) throws IOException {
        //打开选择器
        Selector selector = Selector.open();
        //打开监听通道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);//开启非阻塞模式
        //绑定端口
        socketChannel.socket().bind(new InetSocketAddress(2222));
        //监听客户端连接请求
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);

        selector.select();
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        for (SelectionKey key : selectionKeys) {
            try {
                if(key.isValid()){
                    if(key.isAcceptable()){
                        ServerSocketChannel channel =(ServerSocketChannel) key.channel();
                        SocketChannel accept = channel.accept();//获取实例，意味着完成TCP三次握手
                        if(accept!=null) {//是可用的连接请求
                            accept.configureBlocking(false);//设置为非阻塞
                            //注册为读
                            accept.register(selector, SelectionKey.OP_READ);
                        }
                    }
                    if(key.isReadable()){
                        SocketChannel soc=(SocketChannel) key.channel();
                        //分配1M的字节缓冲区
                        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
                        //读取字节码流，并返回读取到的字节数
                        int readSize = soc.read(buffer);
                        if(readSize>0){
                            String response;
                            //翻转缓冲区
                            buffer.flip();
                            byte[] bytes=new byte[buffer.remaining()];//创建可用大小的字节数组
                            buffer.get(bytes);//将缓冲区数据复制到字节数组中
                            String res = new String(bytes, "UTF-8");
                            System.out.println("res:"+res);
                            //处理数据
                            response="OK|"+res;
                        }else if(readSize<0){
                            System.out.println("server close");
                            key.cancel();
                            soc.close();
                        }
                        if(key!=null){
                            key.cancel();
                            if(key.channel()!=null){
                                key.channel().close();
                            }
                        }
                    }
                }
            }catch (IOException i){
                i.printStackTrace();
            }
        }
    }
}
