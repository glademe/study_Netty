package org.jorry.netty04;

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
 * @date : 2023-06-12 19:36
 * @Describe: 类的描述信息
 */
public class NettyServer {

    private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap.group(boss, worker);
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new StringDecoder());
                pipeline.addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.debug("msg is {}", msg);
                        ctx.fireChannelRead(msg);
                    }
                });

                pipeline.addLast("handler1", new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.info("msg is {}", msg);
                        ctx.fireChannelRead(msg + "11");
                    }
                });

                pipeline.addLast("handler2", new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ch.writeAndFlush("Hello,Jorry");
                    }
                });

                pipeline.addLast("handler_out1", new ChannelOutboundHandlerAdapter() {
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        log.info("out msg is {}", msg);
                        super.write(ctx, msg, promise);
                    }
                });
                pipeline.addLast("handler_out2", new ChannelOutboundHandlerAdapter() {
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        super.write(ctx, msg, promise);
                    }
                });
            }
        });

        serverBootstrap.bind(new InetSocketAddress(9999));
    }
}
