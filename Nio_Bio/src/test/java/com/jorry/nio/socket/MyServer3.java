package com.jorry.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author :Jorry
 * @date : 2023-06-08 22:59
 * @Describe:
 */
public class MyServer3 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(8888));

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select(1000);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey selectionKey = sc.register(selector, SelectionKey.OP_READ);
                    //准备数据
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < 200000; i++) {
                        sb.append("s");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());

                    int write = sc.write(buffer);
                    System.out.println("write = " + write);

                    if (buffer.hasRemaining()) {
                        //为当前的SocketChannel添加write的监听
                        selectionKey.interestOps(selectionKey.interestOps() + SelectionKey.OP_WRITE);
                        //将剩余的数据存储到buffer传递过去
                        selectionKey.attach(buffer);
                    }
                } else if (key.isWritable()) {
                    //获取Buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    //获取Channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    int write = channel.write(buffer);
                    System.out.println("write = " + write);
                    if (!buffer.hasRemaining()) {
                        key.attach(null);
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                    }

                }

            }
        }
    }
}
