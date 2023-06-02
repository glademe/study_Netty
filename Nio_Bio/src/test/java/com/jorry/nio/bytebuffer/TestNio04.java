package com.jorry.nio.bytebuffer;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @author :Jorry
 * @date : 2023-06-02 23:27
 * @Describe: 类的描述信息
 */
public class TestNio04 {

    /**
     * 用于测试 xxx
     */
    @Test
    public void test01() throws IOException {
        String data = "Jorry";
        FileOutputStream os = new FileOutputStream("classpath:temp.txt");
        FileChannel channel = os.getChannel();
        ByteBuffer buffer = Charset.defaultCharset().encode(data);
        channel.write(buffer);
    }


    /**
     * 用于测试 文件的赋值
     */
    @Test
    public void test02() throws IOException {
        FileInputStream is = new FileInputStream("D:\\project\\study_Netty\\Nio_Bio\\src\\main\\resources\\data.txt");
        FileOutputStream os = new FileOutputStream("D:\\project\\study_Netty\\Nio_Bio\\src\\main\\resources\\data2.txt");

        byte[] buffer = new byte[1024];

        while (true) {
            int read = is.read(buffer);
            if (read == -1) break;
            os.write(buffer, 0, read);
        }
    }

    /**
     * 用于测试 xxx
     */
    @Test
    public void test03() throws IOException {
        FileChannel from = new FileInputStream("D:\\project\\study_Netty\\Nio_Bio\\src\\main\\resources\\data.txt").getChannel();
        FileChannel to = new FileOutputStream("D:\\project\\study_Netty\\Nio_Bio\\src\\main\\resources\\data2.txt").getChannel();
        from.transferTo(0, from.size(), to);
    }
}
