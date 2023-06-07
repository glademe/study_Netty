package com.jorry.nio.sample.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;

/**
 * @author :Jorry
 * @date : 2023-06-07 23:31
 * @Describe: 类的描述信息
 */
public class NioServer {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    public static void main(String[] args) {
        new NioServer().bind(8888);
    }

    public void bind(int port) {
        try {
            selector= Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            //设置ServerSocketChannel为非阻塞
            serverSocketChannel.configureBlocking(Boolean.FALSE);
            serverSocketChannel.socket().bind(new InetSocketAddress(8888), 1024);
            //将ServerSocketChannel交由Selector进行管理
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Nio Server start done!");
            new NioServerHandler(selector, Charset.forName("UTF-8")).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
