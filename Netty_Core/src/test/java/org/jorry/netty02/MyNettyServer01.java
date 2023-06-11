package org.jorry.netty02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author :Jorry
 * @date : 2023-06-10 18:45
 * @Describe: 类的描述信息
 */
public class MyNettyServer01 {

    private static final Logger log = LoggerFactory.getLogger(MyNettyServer01.class);

    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        DefaultEventLoopGroup defaultEventLoopGroup = new DefaultEventLoopGroup();
        serverBootstrap.group(boss, worker);

        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new StringDecoder());
                pipeline.addLast(defaultEventLoopGroup, new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.debug("msg is {}", msg);
                    }
                });
            }
        });
        log.debug("server start done !");
        serverBootstrap.bind(new InetSocketAddress(9999));
    }
}
