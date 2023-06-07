package com.jorry.nio.sample.server;

import com.jorry.nio.sample.ChannelAdapter;
import com.jorry.nio.sample.ChannelHandler;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author :Jorry
 * @date : 2023-06-07 23:35
 * @Describe: 类的描述信息
 */
public class NioServerHandler extends ChannelAdapter {
    public NioServerHandler(Selector selector, Charset charset) {
        super(selector, charset);
    }

    @Override
    public void channelRead(ChannelHandler ctx, Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
        ctx.writeAndFlush("hi 我已经收到你的消息Success！\r\n");
    }

    @Override
    public void channelActive(ChannelHandler ctx) {
        try {
            System.out.println("链接报告LocalAddress:" + ctx.channel.getLocalAddress());
            ctx.writeAndFlush("hi! NioServer to msg for you \r\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
