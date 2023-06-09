package org.jorry.netty05;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @author :Jorry
 * @date : 2023-06-15 21:51
 * @Describe: 类的描述信息
 */
public class TestEmbeddedChannel {

    private static final Logger log = LoggerFactory.getLogger(TestEmbeddedChannel.class);

    public static void main(String[] args) {
        ChannelInboundHandlerAdapter h1 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.debug("h1");
                log.debug("msg is {}", msg);
                super.channelRead(ctx, msg);
            }
        };


        ChannelInboundHandlerAdapter h2 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.debug("h2");
                super.channelRead(ctx, msg);
            }
        };

        ChannelInboundHandlerAdapter h3 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                log.debug("h3");
                super.channelRead(ctx, msg);
            }
        };


        ChannelOutboundHandlerAdapter h4 = new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                log.debug("h4");
                super.write(ctx, msg, promise);
            }
        };


        ChannelOutboundHandlerAdapter h5 = new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                log.debug("h5");
                super.write(ctx, msg, promise);
            }
        };


        EmbeddedChannel channel = new EmbeddedChannel(h1, h2, h3, h4, h5);
        channel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("Hi Jorry".getBytes(Charset.defaultCharset())));
    }
}
