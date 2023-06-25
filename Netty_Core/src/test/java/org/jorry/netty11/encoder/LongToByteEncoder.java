package org.jorry.netty11.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author :Jorry
 * @date : 2023-06-25 15:26
 * @Describe: 自定义编码器
 */
public class LongToByteEncoder extends MessageToByteEncoder<String> {

    private static final Logger log = LoggerFactory.getLogger(LongToByteEncoder.class);

    /**
     * @param ctx 获得了ctx等于拿到channel里面的ByteBuf
     * @param msg the message to encode
     * @param out the {@link ByteBuf} into which the encoded message will be written
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        log.debug("encode method invoke");
        String[] split = msg.split("-");
        for (String s : split) {
            long l = Long.parseLong(s);
            out.writeLong(l);
        }

    }
}
