package com.sprint.trace.biz;

import com.lmax.disruptor.EventTranslator;
import com.sprint.trace.domain.TraceCell;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangdi on 16-7-4.
 */
public class TraceCellEventTranslator implements EventTranslator<TraceCell> {

    public final static Logger logger = LoggerFactory.getLogger(TraceCellEventTranslator.class);

    TraceCell traceCell;

    public TraceCellEventTranslator(TraceCell traceCell) {
        this.traceCell = traceCell;
    }

    @Override
    public void translateTo(TraceCell event, long sequence) {
        try {
            PropertyUtils.copyProperties(event, traceCell);
        } catch (Exception e) {
            logger.error("translateTo copyProperties error event={},sequence={}", event, sequence, e);
        }
        //logger.info("push kafka event={},sequence={}", event, sequence);
    }

}
