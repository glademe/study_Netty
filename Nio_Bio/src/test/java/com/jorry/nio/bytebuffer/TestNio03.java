package com.jorry.nio.bytebuffer;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author :Jorry
 * @date : 2023-06-02 22:24
 * @Describe: 类的描述信息
 */
public class TestNio03 {

    /**
     * 用于测试 xxx
     */
    @Test
    public void test01() {
        ByteBuffer buffer = ByteBuffer.allocate(50);
        buffer.put("Hi hezhenbin\n I love y".getBytes());
        doLineSplit(buffer);
        buffer.put("ou\n Do you like me? \n".getBytes());
        doLineSplit(buffer);

    }

    /**
     * 分割后放在新的Buffer中
     *
     * @param buffer
     */
    private static void doLineSplit(ByteBuffer buffer) {
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            if (buffer.get(i) == '\n') {
                int length = i + 1 -buffer.position();
                ByteBuffer newByteBuffer = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    newByteBuffer.put(buffer.get());
                }

                //截取工作完成
                newByteBuffer.flip();
                System.out.println(StandardCharsets.UTF_8.decode(newByteBuffer).toString());
            }
        }
        buffer.compact();
    }
}
