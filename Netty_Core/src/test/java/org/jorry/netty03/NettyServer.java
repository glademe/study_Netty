package org.jorry.netty03;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author :Jorry
 * @date : 2023-06-11 19:17
 * @Describe: 类的描述信息
 */
public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap.group(boss, worker);
        serverBootstrap.childHandler(new ServerChannelInitializer());
        serverBootstrap.bind(new InetSocketAddress(9999));
    }
}
