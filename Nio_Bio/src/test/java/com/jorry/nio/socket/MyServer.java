package com.jorry.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :Jorry
 * @date : 2023-06-03 21:50
 * @Describe: 网络通信服务器端
 */
public class MyServer {
    public static void main(String[] args) throws IOException {
        //1、创建ServerSocketChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //2、设置服务端的监听端口
        ssc.bind(new InetSocketAddress(8080));
        List<SocketChannel> channelList = new ArrayList<>();
        ByteBuffer buffer = ByteBuffer.allocate(20);
        //3、接收客户端的连接
        while (true) {
            System.out.println("等待建立连接....");
            //SocketChannel 代表服务端与客户端链接的一个通道
            SocketChannel socketChannel = ssc.accept();
            System.out.println("服务器建立连接完成...." + socketChannel);
            channelList.add(socketChannel);
            //完成客户端与服务端通信的过程
            for (SocketChannel channel : channelList) {
                System.out.println("开启数据通信...");
                channel.read(buffer);

                buffer.flip();
                System.out.println("Charset.defaultCharset().decode(buffer).toString() = " + Charset.defaultCharset().decode(buffer).toString());
                buffer.clear();
                System.out.println("通信结束....");
            }
        }
    }
}
