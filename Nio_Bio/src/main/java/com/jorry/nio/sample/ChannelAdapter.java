package com.jorry.nio.sample;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author :Jorry
 * @date : 2023-06-07 22:56
 * @Describe: 类的描述信息
 */
@SuppressWarnings("all")
public abstract class ChannelAdapter extends Thread {
    private Selector selector;

    private ChannelHandler channelHandler;
    private Charset charset;

    public ChannelAdapter(Selector selector, Charset charset) {
        this.selector = selector;
        this.charset = charset;
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    handleInput(key);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (!key.isValid()) return;
        //客户端SocketChannel
        Class<?> superclass = key.channel().getClass().getSuperclass();
        if (superclass == SocketChannel.class) {
            SocketChannel sc = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                if (sc.finishConnect()) {
                    channelHandler = new ChannelHandler(sc, charset);
                    channelActive(channelHandler);
                    sc.register(selector, SelectionKey.OP_READ);
                } else {
                    System.exit(1);
                }
            }
        } else if (superclass == ServerSocketChannel.class) {
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = ssc.accept();
                //设置SocketChannel为非阻塞
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
                channelHandler = new ChannelHandler(socketChannel, charset);
                channelActive(channelHandler);
            }
        } else if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int readBytes = socketChannel.write(buffer);
            if (readBytes > 0) {
                //转化为读模式
                buffer.flip();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                channelRead(channelHandler, new String(bytes, charset));
            } else if (readBytes < 0) {
                key.cancel();
                socketChannel.close();
            }
        }

    }

    public abstract void channelRead(ChannelHandler ctx, Object msg);

    public abstract void channelActive(ChannelHandler ctx);
}
