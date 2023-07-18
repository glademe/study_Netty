package org.jorry.netty16;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author :Jorry
 * @date : 2023-07-18 21:50
 * @Describe: 类的描述信息
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger log = LoggerFactory.getLogger(MyWebSocketHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.debug("接受到的client的数据为：{}", msg.text());


        ctx.channel().writeAndFlush(new TextWebSocketFrame(new Date().toString() + "------" + msg.text()));
    }
}
