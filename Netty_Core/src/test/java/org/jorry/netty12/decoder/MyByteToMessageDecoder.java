package org.jorry.netty12.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.jorry.netty12.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author :Jorry
 * @date : 2023-06-25 18:41
 * @Describe: 类的描述信息
 */
public class MyByteToMessageDecoder extends ByteToMessageDecoder {

    private static final Logger log = LoggerFactory.getLogger(MyByteToMessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //1、幻术 5字节
        ByteBuf buf = in.readBytes(5);
        log.debug("幻术是：{}", buf.toString(Charset.defaultCharset()));

        //2、协议版本
        byte protoVersion = in.readByte();
        log.debug("协议版本为：{}", protoVersion);

        //3、序列化方式
        byte serializableType = in.readByte();
        log.debug("序列化方式：{}", serializableType);

        //4、功能指令
        byte funcNo = in.readByte();
        log.debug("功能指令为：{}", funcNo);

        //5、正文长度
        int contentLength = in.readInt();
        log.debug("正文长度为：{}", contentLength);

        //6、正文
        Message message = null;
        if (serializableType == 1) {
            ObjectMapper objectMapper = new ObjectMapper();
            message = objectMapper.readValue(in.readCharSequence(contentLength, Charset.defaultCharset()).toString(), Message.class);
        }
        out.add(message);
    }
}
