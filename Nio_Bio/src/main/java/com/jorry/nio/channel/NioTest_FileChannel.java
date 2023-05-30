package com.jorry.nio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * {@link FileChannel}
 *
 * @author :Jorry
 * @date : 2023-05-30 19:51
 * @Describe: FileChannel 获取Channel
 */
public class NioTest_FileChannel {
    public static void main(String[] args) {
        String path = "D:\\project\\study_Netty\\Nio_Bio\\src\\main\\resources\\data.txt";
        try (FileChannel channel = FileChannel.open(Paths.get(path), StandardOpenOption.READ)) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(10);
            while (true) {
                int read = channel.read(byteBuffer);
                if (read == -1) break;
                //转换为读模式
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    byte b = byteBuffer.get();
                    System.out.println("b = " + (char) b);
                }
                //转换为写模式
                byteBuffer.compact();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
