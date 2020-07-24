package com.rock.port.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by caoqingyuan on 2018/1/3.
 */
public class ClientMain {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);//设为非阻塞方式
        socketChannel.connect(new InetSocketAddress("localhost",2222));//连接到server

        Selector selector = Selector.open();//打开一个选择器
        socketChannel.register(selector, SelectionKey.OP_CONNECT);//把该通道注册到这个选择器中，让这个选择器来监听这个通道

        if (socketChannel.finishConnect()) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            byte[] bytes = "hhhh".getBytes("UTF-8");
            ByteBuffer buffer=ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            //发送缓冲区字节
            socketChannel.write(buffer);
        }
    }
}
