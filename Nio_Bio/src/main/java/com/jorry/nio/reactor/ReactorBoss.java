package com.jorry.nio.reactor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author :Jorry
 * @date : 2023-06-10 9:24
 * @Describe: 类的描述信息
 */
public class ReactorBoss {

    private static final Logger log = LoggerFactory.getLogger(ReactorBoss.class);

    public static void main(String[] args) throws IOException {
        log.debug("server start");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(9999));
        Worker worker = new Worker("worker");

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                iterator.remove();
                if (sk.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);

//                sc.register(selector, SelectionKey.OP_READ);
                    worker.channelHandler(sc);
                }
            }
        }


    }
}
