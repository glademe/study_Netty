package com.jorry.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author :Jorry
 * @date : 2023-06-03 22:11
 * @Describe: 网络通信客户端
 */
public class MyClient {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(9999));
        channel.write(Charset.defaultCharset().encode("xiaohe\n"));
        System.out.println("-----------------");
    }
}
