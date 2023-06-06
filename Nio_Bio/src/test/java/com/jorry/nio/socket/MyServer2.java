package com.jorry.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author :Jorry
 * @date : 2023-06-06 21:52
 * @Describe: 类的描述信息
 */
public class MyServer2 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(8888));
        //设置非阻塞，Selector只有在非阻塞的情况下，才可以进行使用
        ssc.configureBlocking(false);

        //引入监管者
        Selector selector = Selector.open();

        //监管者 管理ServerSocketChannel
        SelectionKey selectionKey = ssc.register(selector, 0, null);
        //设置Select监控SSC Accept
        /**
         *selector
         *    keys -----> HashSet
         *    register注册ssc
         */
        selectionKey.interestOps(SelectionKey.OP_ACCEPT);

        System.out.println("MyServer2.main");
        //监控 管理的ServerSocketChannel
        while (true) {
            //只有监控到了有实际的连接，或者读写操作才会进行处理
            //对应的有ACCEPT状态和READ/WRITE状态的SC存起来   SelectionKeys HashSet
            selector.select();
            System.out.println("-------------------------------");
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    //获取ServerSocketChannel
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = channel.accept();
                    System.out.println("socketChannel = " + socketChannel);
                } else if (key.isReadable()) {

                }
            }
        }
    }
}
