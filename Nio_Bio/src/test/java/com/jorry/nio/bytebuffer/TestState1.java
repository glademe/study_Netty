package com.jorry.nio.bytebuffer;

import junit.framework.TestCase;
import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * {@link ByteBuffer}
 *
 * @author :Jorry
 * @date : 2023-06-01 21:45
 * @Describe: 类的描述信息
 */
public class TestState1 {

    /**
     * ByteBuffer 主要有三种状态
     * 1.position
     * 2.capacity
     * 3.limit
     */

    /**
     * 用于测试NIO状态
     */
    @Test
    public void test01() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println("buffer.position() = " + buffer.position());//0
        System.out.println("buffer.capacity() = " + buffer.capacity());//10
        System.out.println("buffer.limit() = " + buffer.limit());//10
    }

    /**
     * 用于测试 flip() 读模式
     */
    @Test
    public void test02() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{1, 2, 3, 4});
        //转换为读模式
        buffer.flip();
        System.out.println("buffer.position() = " + buffer.position());//0
        System.out.println("buffer.capacity() = " + buffer.capacity());//10
        System.out.println("buffer.limit() = " + buffer.limit());//4
    }


    /**
     * 用于测试 clear() 写模式
     */
    @Test
    public void test03() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{1, 2, 3, 4});
        //转换为写模式
        buffer.clear();
        System.out.println("buffer.position() = " + buffer.position());//4
        System.out.println("buffer.capacity() = " + buffer.capacity());//10
        System.out.println("buffer.limit() = " + buffer.limit());//10
    }


    /**
     * 用于测试 compact()写模式
     */
    @Test
    public void test04() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'1', '2', '3', '4'});

        buffer.flip();
        System.out.println("buffer.position() = " + buffer.position());//2
        System.out.println("buffer.capacity() = " + buffer.capacity());//10
        System.out.println("buffer.limit() = " + buffer.limit());//4
        //转换为写模式
        buffer.compact();
        System.out.println("buffer.position() = " + buffer.position());//4
        System.out.println("buffer.capacity() = " + buffer.capacity());//10
        System.out.println("buffer.limit() = " + buffer.limit());//10

    }
}