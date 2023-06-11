package org.jorry.netty02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author :Jorry
 * @date : 2023-06-10 18:48
 * @Describe: 类的描述信息
 */
public class MyNettyClient01 {

    private static final Logger log = LoggerFactory.getLogger(MyNettyClient01.class);

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group);

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new StringEncoder());
            }
        });

        ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(9999));
//        channelFuture.sync();
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.debug("add listener ...");
                Channel channel = channelFuture.channel();
                channel.writeAndFlush("Hello,Jorry");
            }
        });

        log.debug("client write msg success!");
    }
}
