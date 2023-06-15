package org.jorry.netty06;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;

/**
 * @author :Jorry
 * @date : 2023-06-15 22:29
 * @Describe: 类的描述信息
 */
public class TestByteBuf {
    public static void main(String[] args) {
        //获取ByteBuf,默认的ByteBuf 默认256 最大值为Integer.MAX
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();

        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(10);
        byteBuf.writeByte('a');

        byteBuf.writeInt(10);
        byteBuf.writeInt(11);
        byteBuf.writeInt(12); //十三个字节
        byteBuf.writeInt(13);
        System.out.println(byteBuf);
        System.out.println(ByteBufUtil.prettyHexDump(byteBuf));

    }
}
