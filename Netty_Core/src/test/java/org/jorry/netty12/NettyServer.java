package org.jorry.netty12;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import org.jorry.netty12.decoder.MyByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author :Jorry
 * @date : 2023-06-25 13:48
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
                //封帧操作
                pipeline.addLast(new LengthFieldBasedFrameDecoder(1024, 8, 4, 0, 0));
                pipeline.addLast(new LoggingHandler());
                pipeline.addLast(new MyByteToMessageDecoder());
                pipeline.addLast(new SimpleChannelInboundHandler<Message>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
                        log.debug("==============》 msg is {}", msg);
                    }
                });

            }
        });
        serverBootstrap.bind(new InetSocketAddress(8989));
    }
}
