package org.jorry.netty11.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author :Jorry
 * @date : 2023-06-25 15:45
 * @Describe: 自定义解码器
 */
public class byteToLongDecoder extends ByteToMessageDecoder {

    private static final Logger log = LoggerFactory.getLogger(byteToLongDecoder.class);


    //Decoder处理过程中，如果ByteBuf没有处理完，将重复调用Decoder方法
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.debug("decode method invoke");
        if (in.readableBytes() >= 8) {
            long l = in.readLong();
            out.add(l);
        }
    }
}
