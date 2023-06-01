package com.jorry.nio.bytebuffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @author :Jorry
 * @date : 2023-06-01 22:27
 * @Describe: 类的描述信息
 */
public class TestNio01 {

    /**
     * 用于测试 xxx
     */
    @Test
    public void testRewind() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'1', '2', '3', '4'});

        //设置读模式
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println("buffer.get() = " + (char) buffer.get());
        }
        buffer.rewind();
        while (buffer.hasRemaining()) {
            System.out.println("buffer.get() = " + (char) buffer.get());
        }
    }

    /**
     * 用于测试 ByteBuffer#mark/reset
     */
    @Test
    public void testMark() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'1', '2', '3', '4'});
        buffer.flip();
        System.out.println("buffer.get() = " + (char) buffer.get());
        System.out.println("buffer.get() = " + (char) buffer.get());
        buffer.mark();
        System.out.println("buffer.get() = " + (char) buffer.get());
        System.out.println("buffer.get() = " + (char) buffer.get());
        buffer.reset();
        System.out.println("buffer.get() = " + (char) buffer.get());
        System.out.println("buffer.get() = " + (char) buffer.get());
    }
}
