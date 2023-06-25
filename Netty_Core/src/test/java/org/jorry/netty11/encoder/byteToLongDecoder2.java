package org.jorry.netty11.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author :Jorry
 * @date : 2023-06-25 15:45
 * @Describe: 自定义解码器
 */
public class byteToLongDecoder2 extends ReplayingDecoder {

    private static final Logger log = LoggerFactory.getLogger(byteToLongDecoder2.class);


    //Decoder处理过程中，如果ByteBuf没有处理完，将重复调用Decoder方法
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        long l = in.readLong();
        out.add(l);
    }
}
