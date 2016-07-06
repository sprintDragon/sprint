package com.sprint.trace;

import com.sprint.trace.api.IDemoBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by wangdi on 16-7-4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-config.xml"})
public class TestDemoBiz {

    @Resource
    IDemoBiz iDemoBiz;

    @Test
    public void testDemo() throws Exception {
        iDemoBiz.queryOrder(22l, 11l);
    }

    volatile boolean lock = true;

    @Test
    public void testMuti() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        long nowTime = System.nanoTime();
        long num = 0;
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock = false;
            }
        });
        while (lock) {
            Future<String> f = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return iDemoBiz.queryOrder(System.currentTimeMillis(), 11l);
                }
            });
            num++;
            Thread.sleep(1);
        }
        System.out.println("时间消耗 : " + (System.nanoTime() - nowTime) + ",方法执行条数：" + num);
    }
}
