package com.sprint.trace.distruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * Created by wangdi on 16-7-4.
 */
public class DisruptorFacade<T> implements IDisruptorFacade {

    Disruptor<T> disruptor;

    public DisruptorFacade(Disruptor<T> disruptor, EventHandler<? super T>... handlers) {
        this.disruptor = disruptor;
        disruptor.handleEventsWith(handlers);
    }

    public Disruptor<T> getDisruptor() {
        return disruptor;
    }

    public void start() {
        disruptor.start();
    }
}
