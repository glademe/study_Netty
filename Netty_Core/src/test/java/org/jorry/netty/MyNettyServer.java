package org.jorry.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author :Jorry
 * @date : 2023-06-10 17:00
 * @Describe: Netty服务端
 */
public class MyNettyServer {

    private static final Logger log = LoggerFactory.getLogger(MyNettyServer.class);

    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        //创建了一组线程,通过死循环在监控我们的状态，Accept，Read,Write
        NioEventLoopGroup group = new NioEventLoopGroup();
        serverBootstrap.group(group);

        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            /**
             *channel接通 监控的Accept rw
             */
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                //字符串解码器
                pipeline.addLast(new StringDecoder());
                //自定义Handler
                pipeline.addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.debug("解码的消息为msg:{}", msg);
                    }
                });
            }
        });

        serverBootstrap.bind(new InetSocketAddress(9999));
    }
}
