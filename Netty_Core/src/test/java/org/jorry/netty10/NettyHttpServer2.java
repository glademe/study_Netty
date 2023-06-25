package org.jorry.netty10;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;

/**
 * @author :Jorry
 * @date : 2023-06-25 13:48
 * @Describe: 类的描述信息
 */
public class NettyHttpServer2 {

    private static final Logger log1 = LoggerFactory.getLogger(NettyHttpServer2.class);
    private static final Logger log = LoggerFactory.getLogger(NettyHttpServer2.class);

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
                //http相关的编解码器,把一个HTTP协议解码成2个Message，分别是HttpRequest,HttpContent
                //在处理过程中很麻烦
                //1.SimpleChannelInboundHandler
                pipeline.addLast(new HttpServerCodec());
                pipeline.addLast(new HttpObjectAggregator(1024));

                pipeline.addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        DefaultFullHttpRequest request = (DefaultFullHttpRequest) msg;
                        HttpHeaders headers = request.headers();
                        ByteBuf content = request.content();
                        String uri = request.uri();
                        HttpVersion httpVersion = request.protocolVersion();

                        DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpVersion, HttpResponseStatus.OK);
                        byte[] bytes = "Hello,Jorry".getBytes(Charset.defaultCharset());
                        response.headers().set(CONTENT_LENGTH, bytes.length);
                        response.content().writeBytes(bytes);
                        ctx.writeAndFlush(response);
                    }
                });
            }
        });
        serverBootstrap.bind(new InetSocketAddress(8989));
    }
}
