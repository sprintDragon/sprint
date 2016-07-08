package com.sprint.trace.biz;

import com.sprint.trace.distruptor.MultiEvenHandler;
import com.sprint.trace.domain.TraceCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangdi on 16-7-4.
 */
public class TraceCellEventHandler implements MultiEvenHandler<TraceCell> {

    public final static Logger logger = LoggerFactory.getLogger(TraceCellEventTranslator.class);

    ExecutorService executorService = Executors.newFixedThreadPool(100);

    @Override
    public void onEvent(TraceCell event, long sequence, boolean endOfBatch) throws Exception {
        logger.info("consumer kafka event={},sequence={},endOfBatch={}", event, sequence, endOfBatch);

//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                      Thread.sleep(30);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    @Override
    public void onEvent(List<TraceCell> event, long sequence, boolean endOfBatch) throws Exception {
        //logger.info("consumer2 kafka event={},sequence={},endOfBatch={}", event, sequence, endOfBatch);
        Thread.sleep(100);
    }
}
