package org.jorry.netty06;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author :Jorry
 * @date : 2023-06-15 22:29
 * @Describe: 类的描述信息
 */
public class TestByteBuf {

    private static final Logger log = LoggerFactory.getLogger(TestByteBuf.class);

    public static void main(String[] args) {
         createDirectBufBufOrHeapByteBuffer();

    }

    private static void createDirectBufBufOrHeapByteBuffer() {
        //1、直接内存
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        log.debug("{}", buffer);
        //2、创建直接内存的ByteBuf
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.directBuffer();

        //---------------------创建HeapBytBuf----------------------
        ByteBuf byteBuf1 = ByteBufAllocator.DEFAULT.heapBuffer();
    }

    /**
     * BytBuf内存空间
     */
    private static void extractedSpace() {
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
