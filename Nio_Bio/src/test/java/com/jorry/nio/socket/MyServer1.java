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
 * @date : 2023-06-03 22:48
 * @Describe: 类的描述信息
 */
public class MyServer1 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ByteBuffer buffer = ByteBuffer.allocate(20);
        //设置ServerSocketChannel为非阻塞
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(9999));
        List<SocketChannel> list = new ArrayList<>();
        while (true) {
            SocketChannel sc = ssc.accept();
            if (sc != null) {
                sc.configureBlocking(false);
                list.add(sc);
            }
            for (SocketChannel socketChannel : list) {
                int read = socketChannel.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    System.out.println("Charset.defaultCharset().decode(buffer) = " + Charset.defaultCharset().decode(buffer));
                    buffer.clear();
                }
            }
        }
    }
}
