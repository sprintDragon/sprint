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
    final int executors = 4*2+1;
    final long wait_time = 1000*60*5;

    @Test
    public void testMuti() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(executors);
        executorService.submit(new Runnable()
        {
            @Override
            public void run() {
                try {
                    Thread.sleep(wait_time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock = false;
            }
        });
        for (int i = 0; i < executors; i++)
        {
            executorService.submit(new Runnable()
            {
                @Override
                public void run() {
                    long nowTime = System.nanoTime();
                    long num = 0;
                    while (lock) {
                        iDemoBiz.queryOrder(System.currentTimeMillis(), 11l);
                        num++;
                        try
                        {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("时间消耗 : " + (System.nanoTime() - nowTime) + ",方法执行条数：" + num);
                }
            });
        }
    }
}
