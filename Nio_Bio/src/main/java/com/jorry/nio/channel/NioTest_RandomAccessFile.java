package com.jorry.nio.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * {@link RandomAccessFile}
 *
 * @author :Jorry
 * @date : 2023-05-30 19:41
 * @Describe: RandomAccessFile获取Channel
 */
public class NioTest_RandomAccessFile {
    public static void main(String[] args) {
        FileChannel channel = null;
        try {
            RandomAccessFile ras = new RandomAccessFile("D:\\project\\study_Netty\\Nio_Bio\\src\\main\\resources\\data.txt", "rw");
            //获取Channel
            channel = ras.getChannel();
            //创建ByteBuffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            while (true) {
                //将Channel的数据写入到ByteBuffer
                int read = channel.read(byteBuffer);
                if (read == -1) break;
                //设置读模式
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    byte b = byteBuffer.get();
                    System.out.println("b = " + (char) b);
                }
                //转化为写模式
                byteBuffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (channel == null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
