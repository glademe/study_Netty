package org.jorry.netty02;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.TimeUnit;

/**
 * @author :Jorry
 * @date : 2023-06-10 19:56
 * @Describe: 类的描述信息
 */
public class TestEventLoopGroup {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);
        EventLoop eventLoop = eventLoopGroup.next();
        EventLoop eventLoop1 = eventLoopGroup.next();

        EventLoopGroup group = new DefaultEventLoopGroup();
        EventLoop next = group.next();
        next.scheduleAtFixedRate(() -> {
            System.out.println("1");
        }, 1, 2, TimeUnit.SECONDS);
        System.out.println(eventLoop);
        System.out.println(eventLoop1);

//        System.out.println(NettyRuntime.availableProcessors());
    }
}
