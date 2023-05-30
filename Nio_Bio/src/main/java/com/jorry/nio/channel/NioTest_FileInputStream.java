package com.jorry.nio.channel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * {@link FileChannel}
 *
 * @author :Jorry
 * @date : 2023-05-30 19:06
 * @Describe: FileChannel获取Channel
 */
public class NioTest_FileInputStream {
    public static void main(String[] args) {

        try {
            //1.获取文件输入流
            FileInputStream is = new FileInputStream("D:\\project\\study_Netty\\Nio_Bio\\src\\main\\resources\\data.txt");
            //2.获取FileChannel
            FileChannel channel = is.getChannel();
            //3.创建缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            while (true) {
                //4.将通道中的消息写入缓冲区
                int read = channel.read(byteBuffer);
                if (read == -1) {
                    break;
                }
                //程序读取buffer中的数据，要将Buffer转换为读模式
                byteBuffer.flip();
                //循环读取缓冲区中的数据
                while (byteBuffer.hasRemaining()) {
                    byte b = byteBuffer.get();
                    System.out.println("b = " + (char) b);
                }
                //设置写模式,可以进行后续数据的写入
                byteBuffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
