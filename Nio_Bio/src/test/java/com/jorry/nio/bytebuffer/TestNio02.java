package com.jorry.nio.bytebuffer;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @author :Jorry
 * @date : 2023-06-01 22:53
 * @Describe: 类的描述信息
 */
public class TestNio02 {

    /**
     * 用于测试 xxx
     */
    @Test
    public void test01() {
        //写入字符串，英文
//        ByteBuffer buffer = ByteBuffer.allocate(10);
//        buffer.put("Jorry".getBytes());
//        buffer.flip();
//        while (buffer.hasRemaining()) {
//            System.out.println("buffer.get() = " + (char) buffer.get());
//        }
//        buffer.clear();
        ByteBuffer buffer = Charset.forName("UTF-8").encode("hezhenbin");
        if (buffer.hasRemaining()) {
            byte b = buffer.get();
            System.out.println("b = " + (char)b);
        }
    }
}
