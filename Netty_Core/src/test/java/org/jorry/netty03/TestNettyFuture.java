package org.jorry.netty03;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author :Jorry
 * @date : 2023-06-11 21:19
 * @Describe: 类的描述信息
 */
public class TestNettyFuture {

    private static final Logger log1 = LoggerFactory.getLogger(TestNettyFuture.class);
    private static final Logger log = LoggerFactory.getLogger(TestNettyFuture.class);

    public static void main(String[] args) {
        DefaultEventLoopGroup eventLoopGroup = new DefaultEventLoopGroup(2);
        EventLoop eventLoop = eventLoopGroup.next();

        Future<Integer> future = eventLoop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                try {
                    log.debug("异步操作处理....");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {

                }
                return 1024;
            }
        });


        log.debug("可以接收异步处理了");
        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log1.debug("future is {}", future.get());
            }
        });
    }
}
