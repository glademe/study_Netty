package com.jorry.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author :Jorry
 * @date : 2023-06-08 23:08
 * @Describe: 类的描述信息
 */
public class MyClient2 {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8888));
        int read = 0;
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            read += socketChannel.read(buffer);
            System.out.println("read = " + read);
            buffer.clear();
        }

    }
}
