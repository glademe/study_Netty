package org.jorry.netty06;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;

/**
 * @author :Jorry
 * @date : 2023-06-24 21:49
 * @Describe: 类的描述信息
 */
public class TestByteBuf3 {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();

        buffer.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'});

        System.out.println(buffer);
        System.out.println(ByteBufUtil.prettyHexDump(buffer));


        ByteBuf s1 = buffer.slice(0, 4);

        s1.retain();
        ByteBuf s2 = buffer.slice(4, 2);
        s2.retain();
        buffer.release();
        System.out.println(ByteBufUtil.prettyHexDump(s1));

        System.out.println(ByteBufUtil.prettyHexDump(s2));


    }
}
