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

> FileChannel

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

## 2、ByteBuffer

### 2.1、ByteBuffer是抽象类，他的主要实现类为：

```markdown
1、HeapByteBuffer 堆 【JVM内的堆内存】
2、MappedByteBuffer(DirectByteBuffer) 【OS内存】
```

- 获得方式

```markdown
ByteBuffer.allocate(10);
encode()
```

- 核心结构

```markdown
ByteBuffer是一个类似数组的结构，整个结构包含三个主要的状态
1.Capacity
buffer的容量，类似于数组的Size
2.Position
buffer当前缓存的下标，在读取操作时记录读取了哪个位置，在写操作时记录写到了哪个位置，从0开始，每读取一次，下标+1
3.Limit
读写限制，在读操作时，设置了你能读多少字节的数据，在写操作时，设置了你还能写多少字节的数据
```

```markdown
1、ByteBuffer
2、CharBuffer
3、DoubleBuffer
4、IntBuffer
5、LongBuffer
6、ShortBuffer
7、MappedByteBuffer
```

- 模式

```markdown
1.写模式:新创建clear()获得外部的数据（文件，网络数据）
2.读模式:filp()程序读取buffer中的数据，为了程序使用
``` 

### 2.2、ByteBuffer核心API

- buffer中写入数据

```markdown
1.channel的read方法
2.buffer的put方法
```

- 从buffer中读取数据

```markdown
1.channel的write方法
2.buffer的get方法//每调用一次，position会发生改变
3.mark&reset方法，通过mark方法进行标记（position）,通过reset方法跳回标记，重新执行
4.rewind方法，可以将position重置为0，用于赋值
5.get(i)方法，获取特定position上的数据，但不会对position的位置产生影响
```

### 2.3、ByteBuffer字符串操作

- 2.3.1、字符串存储到Buffer中

```markdown
ByteBuffer buffer = ByteBuffer.allocate(10)
buffer.put("Jorry",getBytes())

buffer.flip()
while(buffer.hasRemaing()){
System.out.println(buffer.get())
}

buffer.clear()

ByteBuffer buffer = Charset.forName("UTF-8").encode("Jorry");
1.encode方法字段把字符串按照字符编码后，存储到ByteBuffer
2.自动把ByteBuffer设置成读模式，且不能手工调用flip方法
ByteBuffer buffer = StandardCharsets.UTF_8.encode("Jorry");
ByteBuffer buffer = ByteBuffer.wrap("Jorry".getBytes(StandardCharsets.UTF_8));
```

- 2.3.2、Buffer中的数据转化为字符串

```markdown
ByteBuffer buffer = ByteBuffer.allocate(10);
buffer.put("和".getBytes(StandardCharsets.UTF_8));
buffer.flip();
String name = StandardCharsets.UTF_8.decode(buffer).toString();
System.out.println("name = " + name);
```

```java
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

```

## 3、NIO的开发使用
### 3.1、文件操作
#### 3.1.1、读取文件内容
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
### 3.1.2、写入文件
```java
public class TestNio04 {

    /**
     * 用于测试 文件写入操作
     */
    @Test
    public void test01() throws IOException {
        String data = "Jorry";
        FileOutputStream os = new FileOutputStream("classpath:temp.txt");
        FileChannel channel = os.getChannel();
        ByteBuffer buffer = Charset.defaultCharset().encode(data);
        channel.write(buffer);
    }
}

```
> Channel方式文件复制
```java
public class TestNio{
    @Test
    public void test03() throws IOException {
        FileChannel from = new FileInputStream("D:\\project\\study_Netty\\Nio_Bio\\src\\main\\resources\\data.txt").getChannel();
        FileChannel to = new FileOutputStream("D:\\project\\study_Netty\\Nio_Bio\\src\\main\\resources\\data2.txt").getChannel();
        //传输数据上限 2G-1
        from.transferTo(0, from.size(), to);
    }
}
```

## 3、网络编程

```markdown
1.服务端 接收请求 ---------> ServerSocketChannel
2.进行实际的通讯 -----------> SocketChannel
```

- 服务端

```java
public class MyServer {
    public static void main(String[] args) throws IOException {
        //1、创建ServerSocketChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //2、设置服务端的监听端口
        ssc.bind(new InetSocketAddress(8080));
        List<SocketChannel> channelList = new ArrayList<>();
        ByteBuffer buffer = ByteBuffer.allocate(20);
        //3、接收客户端的连接
        while (true) {
            System.out.println("等待建立连接....");
            //SocketChannel 代表服务端与客户端链接的一个通道
            SocketChannel socketChannel = ssc.accept(); //连接阻塞，程序等待client
            System.out.println("服务器建立连接完成...." + socketChannel);
            channelList.add(socketChannel);
            //完成客户端与服务端通信的过程
            for (SocketChannel channel : channelList) {
                System.out.println("开启数据通信...");
                channel.read(buffer);//IO阻塞，等待客户端写入数据
                buffer.flip();
                System.out.println("Charset.defaultCharset().decode(buffer).toString() = " + Charset.defaultCharset().decode(buffer).toString());
                buffer.clear();
                System.out.println("通信结束....");
            }
        }
    }
}
```

- 客户端

```java
public class MyClient {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.connect(new InetSocketAddress(8080));
        System.out.println("-----------------");
    }
}
```
### 3.1、EventLoop
> 主要作用

```markdown
1.事件监听，监听Accept,READ,WRITE事件
2.曾经学到的worker
    - 1、独立线程
    - 2、通过死循环 监控状态进行操作
while(true){
        selector.select
        SelectionKeys 遍历
}
3.selector

EventLoop     worker 线程 select ---> READ WRITE
              boss   线程 select ---> Accept
开发中 如何过的EventLoop
1. 不会通过构造方法 让程序员创建。
2. 通过EventLoopGroup创建
```
### 3.2、EventLoopGroup

```markdown
1. 编程的过程中，开放的编程接口 EventLoopGroup
2. EventLoopGroup 创建EventLoop(一个线程) 多个EventLoop(多个线程)
                  管理EventLoop
                  EventLoopGroup EventLoop的工厂
```
> NioEventLoop和DefaultEventLoop区别

```markdown
1. NioEventLoop 是一个线程  IO Write Read 事件监控
2. DefaultEventLooop 就是一个普通的线程，内容工作可以由程序员决定，他不做 IO监控 读写的处理.

注意：后续再Netty进行多线程开发，推荐大家优先考虑DefaultEventLoop -->普通线程。
```


> 设置协议

```markdown
1、协议头
  幻术（魔数）
2、协议版本号
3、指令类型
4、正文长度
5、序列化方式
5、协议正文（协议体）消息正文
```

```json
{
  "magicCode": "COFFEEBABY",
  "versionID": 1,
  "type": 1,
  "content-length": 18,
  "content": "xxxxxxxxxxx"
}
```