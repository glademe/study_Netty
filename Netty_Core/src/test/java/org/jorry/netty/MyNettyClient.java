package org.jorry.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @author :Jorry
 * @date : 2023-06-10 17:18
 * @Describe: 类的描述信息
 */
public class MyNettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);

        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group);

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                //添加编码器
                ch.pipeline().addLast(new StringEncoder());
            }
        });

        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(9999));
        channelFuture.sync();
        //创建了新的线程，进行写操作
        Channel channel = channelFuture.channel();
        channel.write("Hello,Jorry");
        channel.flush();
    }
}
