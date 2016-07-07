package com.sprint.trace.distruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventProcessorFactory;

/**
 * Created by wangdi on 16-7-4.
 */
public class DisruptorFacade<T> implements IDisruptorFacade {

    Disruptor<T> disruptor;

    public DisruptorFacade(Disruptor<T> disruptor, EventProcessorFactory... eventProcessorFactories) {
        this.disruptor = disruptor;
        disruptor.handleEventsWith(eventProcessorFactories);
    }

    public Disruptor<T> getDisruptor() {
        return disruptor;
    }

    public void start() {
        disruptor.start();
    }

    public void shutdown() {
        disruptor.shutdown();
    }
}
