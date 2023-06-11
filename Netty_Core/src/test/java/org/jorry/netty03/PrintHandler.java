package org.jorry.netty03;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author :Jorry
 * @date : 2023-06-11 20:31
 * @Describe: 类的描述信息
 */
public class PrintHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(PrintHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("msg is {}", msg);
    }
}
