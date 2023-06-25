package org.jorry.netty10;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
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
public class NettyHttpServer1 {

    private static final Logger log = LoggerFactory.getLogger(NettyHttpServer1.class);

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
                //http相关的编解码器
                pipeline.addLast(new HttpServerCodec());

                //限定关注的消息类型
                pipeline.addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                        HttpMethod method = msg.method();
                        HttpHeaders headers = msg.headers();
                        String uri = msg.uri();
                        HttpVersion httpVersion = msg.protocolVersion();

                        DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpVersion, HttpResponseStatus.OK);

                        byte[] bytes = "<h1>Hello,Jorry</h1>".getBytes(Charset.defaultCharset());
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
