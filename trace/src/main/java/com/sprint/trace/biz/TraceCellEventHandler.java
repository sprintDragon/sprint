package com.sprint.trace.biz;

import com.lmax.disruptor.EventHandler;
import com.sprint.trace.domain.TraceCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangdi on 16-7-4.
 */
public class TraceCellEventHandler implements EventHandler<TraceCell> {

    public final static Logger logger = LoggerFactory.getLogger(TraceCellEventTranslator.class);

    @Override
    public void onEvent(TraceCell event, long sequence, boolean endOfBatch) throws Exception {
        logger.info("consumer kafka event={},sequence={},endOfBatch={}", event, sequence, endOfBatch);
    }

}
