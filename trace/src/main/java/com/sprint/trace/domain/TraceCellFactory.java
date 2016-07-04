package com.sprint.trace.domain;

import com.lmax.disruptor.EventFactory;

/**
 * Created by wangdi on 16-7-4.
 */
public class TraceCellFactory implements EventFactory<TraceCell> {

    @Override
    public TraceCell newInstance() {
        return new TraceCell();
    }

}
