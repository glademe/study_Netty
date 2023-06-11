package org.jorry.netty03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author :Jorry
 * @date : 2023-06-11 21:03
 * @Describe: 类的描述信息
 */
public class TestJdkFuture {

    private static final Logger log = LoggerFactory.getLogger(TestJdkFuture.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("异步处理工作....");
                TimeUnit.SECONDS.sleep(1);
                return 1024;
            }
        });

        log.debug("可以处理结果了");
        log.debug("result:{}", future.get());
        log.debug("---------------------");
    }
}
