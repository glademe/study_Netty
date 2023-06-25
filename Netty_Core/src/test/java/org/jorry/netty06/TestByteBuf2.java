package org.jorry.netty06;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;

/**
 * @author :Jorry
 * @date : 2023-06-24 17:28
 * @Describe: 类的描述信息
 */
public class TestByteBuf2 {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10);


        buffer.writeByte(5);

        System.out.println("buffer = " + buffer);

        System.out.println(ByteBufUtil.prettyHexDump(buffer));
        buffer.markReaderIndex();
        byte b = buffer.readByte();
        System.out.println("b = " + b);
        System.out.println("buffer = " + buffer);
        System.out.println(ByteBufUtil.prettyHexDump(buffer));

        //设置重复读
        buffer.resetReaderIndex();
        byte b2 = buffer.readByte();
        System.out.println("b2 = " + b2);
        System.out.println("buffer = " + buffer);
        System.out.println(ByteBufUtil.prettyHexDump(buffer));
    }
}
