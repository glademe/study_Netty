package com.jorry.nio.bytebuffer;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
//        ByteBuffer buffer = Charset.forName("UTF-8").encode("hezhenbin");
//        ByteBuffer buffer = StandardCharsets.UTF_8.encode("Jorry");
        ByteBuffer buffer = ByteBuffer.wrap("Jorry".getBytes(StandardCharsets.UTF_8));
        while (buffer.hasRemaining()) {

            byte b = buffer.get();
            System.out.println("b = " + (char) b);
        }
    }


     /**
      * 用于测试 xxx
      */
     @Test
     public void test02() {
         ByteBuffer buffer = ByteBuffer.allocate(10);
         buffer.put("和".getBytes(StandardCharsets.UTF_8));

         buffer.flip();
         String name = StandardCharsets.UTF_8.decode(buffer).toString();
         System.out.println("name = " + name);
     }
}
