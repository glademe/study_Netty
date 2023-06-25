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
public class NettyHttpServer {

    private static final Logger log = LoggerFactory.getLogger(NettyHttpServer.class);

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

                pipeline.addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        if (msg instanceof HttpRequest) {
                            HttpRequest request = (HttpRequest) msg;
                            String uri = request.uri();
                            if (uri.contains("/hello")) {
                                HttpHeaders headers = request.headers();
                                HttpVersion httpVersion = request.protocolVersion();
                                HttpMethod method = request.method();
                                log.debug("uri is {},{},{},{}", uri, headers, httpVersion, method);
                                //创建Response对象
                                DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                                byte[] bytes = "<h1>Hello,Jorry</h1>".getBytes(Charset.defaultCharset());
                                //设置响应内容的长度
                                response.headers().set(CONTENT_LENGTH, bytes.length);
                                //写入数据
                                response.content().writeBytes(bytes);
                                ctx.writeAndFlush(response);
                            }
                        } else if (msg instanceof HttpContent) {
                            HttpContent httpContent = (HttpContent) msg;
                            ByteBuf content = httpContent.content();
                            log.debug("content is {}", content);
                        }

                    }
                });
            }
        });
        serverBootstrap.bind(new InetSocketAddress(8989));
    }
}
