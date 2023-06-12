package org.jorry.netty04;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @author :Jorry
 * @date : 2023-06-12 19:36
 * @Describe: 类的描述信息
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup);
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new LoggingHandler());
                pipeline.addLast(new StringEncoder());
            }
        });

        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(9999));
        channelFuture.sync();
        Channel channel = channelFuture.channel();
        channel.writeAndFlush("你好");
        channel.close().sync();

        eventLoopGroup.shutdownGracefully();
    }
}
