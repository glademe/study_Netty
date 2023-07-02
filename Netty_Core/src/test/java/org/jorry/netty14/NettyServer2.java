package org.jorry.netty14;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author :Jorry
 * @date : 2023-06-25 13:48
 * @Describe: 类的描述信息
 */
public class NettyServer2 {

    private static final Logger log = LoggerFactory.getLogger(NettyServer2.class);

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
                pipeline.addLast(new LoggingHandler());
                pipeline.addLast(new StringDecoder());
                pipeline.addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        if (true) {
                            throw new RuntimeException("错误模拟");
                        }
                        super.channelRead(ctx, msg);
                    }

                });

                pipeline.addLast(new ChannelInboundHandlerAdapter(){
                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        log.debug("异常被捕获了，原因为：{}",cause);
                        super.exceptionCaught(ctx, cause);
                    }
                });

            }
        });
        serverBootstrap.bind(new InetSocketAddress(8989));
    }
}
