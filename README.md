# Netty学习笔记

## 1、Channel
> 文件操作

- FileChannel

> 网络操作

- SocketChannel 通过TCP读写网络中的数据
- ServerSocketChannel 监听新进来的TCP连接，像Web服务那样，对每一个新进来的连接都会创建一个SocketChannel
- DatagramChannel，通过UDP读写网络中的数据

> 获得方式

```markdown
1、FileInputStream/FileOutputStream
2、RandomAccessFile
3、Socket
4、ServerSocket
5、DatagramSocket
```
### 1.1.获取channel的方式
> FileInputStream

```java
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
```
> RandomAccessFile

```java
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
```
>FileChannel

```java
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
```

## 2、Buffer
```markdown
1、ByteBuffer
2、CharBuffer
3、DoubleBuffer
4、IntBuffer
5、LongBuffer
6、ShortBuffer
7、MappedByteBuffer
```
- 获得方式


```java
ByteBuffer.allocate(10);
encode()
```
- 模式

```markdown
1.写模式:新创建clear()获得外部的数据（文件，网络数据）
2.读模式：filp()程序读取buffer中的数据，为了程序使用
``` 

## 2、ByteBuffer
### 2.1、ByteBuffer是抽象类，他的主要实现类为：
```markdown
1、HeapByteBuffer
2、MappedByteBuffer(DirectByteBuffer)
```