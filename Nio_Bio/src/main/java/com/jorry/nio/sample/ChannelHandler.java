package com.jorry.nio.sample;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author :Jorry
 * @date : 2023-06-07 22:49
 * @Describe: 类的描述信息
 */
public class ChannelHandler {
    public SocketChannel channel;

    public Charset charset;

    public ChannelHandler(SocketChannel channel, Charset charset) {
        this.channel = channel;
        this.charset = charset;
    }

    public void writeAndFlush(Object msg) {
        try {
            byte[] bytes = msg.toString().getBytes(charset);
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            channel.write(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
