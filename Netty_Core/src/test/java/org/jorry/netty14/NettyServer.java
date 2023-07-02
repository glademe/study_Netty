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
public class NettyServer {

    private static final Logger log3 = LoggerFactory.getLogger(NettyServer.class);
    private static final Logger log2 = LoggerFactory.getLogger(NettyServer.class);
    private static final Logger log1 = LoggerFactory.getLogger(NettyServer.class);
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
                pipeline.addLast(new LoggingHandler());
                pipeline.addLast(new StringDecoder());
                pipeline.addLast(new ChannelInboundHandlerAdapter() {


                    //handler添加到pipline中
                    @Override
                    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                        super.handlerAdded(ctx);
                    }

                    //handler从pipline中移除
                    @Override
                    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                        super.handlerRemoved(ctx);
                    }

                    //当连接上后，channel的处理被分配给worker线程后
                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        log.debug("Registered invoke");
                        super.channelRegistered(ctx);
                    }

                    //channel的准备工作基本完成，所有的pipline上面的handler添加完成，channel准备就绪
                    //channel方法被回调了，就说明
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        log.debug("active invoke");
                        super.channelActive(ctx);
                    }


                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.debug("read invoke");
                        super.channelRead(ctx, msg);
                    }

                    @Override
                    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                        log.debug("ReadComplete invoke");
                        ctx.channel().close();
                        super.channelReadComplete(ctx);
                    }

                    //连接被断开时，进行调用
                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        log2.debug("channelInActive Invoke");
                        super.channelInactive(ctx);
                    }

                    //work线程服务完了，将线程还给EventLoop
                    @Override
                    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
                        log3.debug("channelUnregistered invoke");
                        super.channelUnregistered(ctx);
                    }

                });

            }
        });
        serverBootstrap.bind(new InetSocketAddress(8989));
    }
}
