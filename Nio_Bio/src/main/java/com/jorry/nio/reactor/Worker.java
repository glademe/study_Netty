package com.jorry.nio.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author :Jorry
 * @date : 2023-06-10 9:33
 * @Describe: 类的描述信息
 */
public class Worker implements Runnable {
    private Selector selector;

    private Thread thread;

    private String threadName;

    private volatile boolean isCreated;


    private ConcurrentLinkedDeque<Runnable> linkedDeque = new ConcurrentLinkedDeque<>();


    public Worker(String threadName) {
        this.threadName = threadName;

    }

    public void channelHandler(SocketChannel sc) throws IOException {
        if (!isCreated) {
            thread = new Thread(this, this.threadName);
            selector = Selector.open();
            thread.start();
            isCreated = true;
        }

        linkedDeque.add(() -> {
            try {
                sc.register(selector, SelectionKey.OP_READ);
            } catch (ClosedChannelException e) {
                throw new RuntimeException(e);
            }
        });

        selector.wakeup();

    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select();
                Runnable poll = linkedDeque.poll();
                if (poll != null) {
                    poll.run();
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey sk = iterator.next();
                    iterator.remove();
                    if (sk.isReadable()) {
                        SocketChannel sc = (SocketChannel) sk.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(20);
                        sc.read(buffer);
                        buffer.flip();
                        System.out.println("数据为：" + Charset.defaultCharset().decode(buffer));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
