package org.jorry.netty03;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.DefaultPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author :Jorry
 * @date : 2023-06-11 21:59
 * @Describe: 类的描述信息
 */
public class TestNettyPromise {

    private static final Logger log = LoggerFactory.getLogger(TestNettyPromise.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        DefaultEventLoopGroup defaultEventLoopGroup = new DefaultEventLoopGroup(2);
        EventLoop eventLoop = defaultEventLoopGroup.next();
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(() -> {
            log.debug("异步处理...");
            try {
                TimeUnit.SECONDS.sleep(1);
                promise.setSuccess(1024);
            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
                promise.setFailure(e.getCause());
            }
        }).start();

        log.debug("等待异步处理的结果");
        log.debug("结果是：{}", promise.get());
    }
}
