package org.jorry.netty12.encoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.jorry.netty12.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @author :Jorry
 * @date : 2023-06-25 17:26
 * @Describe: 自定义编码器
 */
public class MyMessageToByteEncoder extends MessageToByteEncoder<Message> {

    private static final Logger log = LoggerFactory.getLogger(MyMessageToByteEncoder.class);

    /**
     * 编码器
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        log.debug("encoder method invoke");
        //1、幻术
        out.writeBytes(new byte[]{'J', 'o', 'r', 'r', 'y'});//5
        //2、协议版本
        out.writeByte(1);//1
        //3、序列化方式 1、json 2、protobuf
        out.writeByte(1);//1
        //4、功能指令
        out.writeByte(1);//1
        //5、正文长度
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(msg);
        out.writeInt(content.length());//4
        //6、正文
        out.writeCharSequence(content, Charset.defaultCharset());

    }
}
