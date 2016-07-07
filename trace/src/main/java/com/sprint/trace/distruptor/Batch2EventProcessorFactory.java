package com.sprint.trace.distruptor;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.dsl.EventProcessorFactory;

/**
 * Created by stereo on 16-7-7.
 */
public class Batch2EventProcessorFactory<T> implements EventProcessorFactory<T>{

    private final MultiEvenHandler<T> eventHandler;

    public Batch2EventProcessorFactory(MultiEvenHandler<T> eventHandler){
        this.eventHandler = eventHandler;
    }
    @Override
    public EventProcessor createEventProcessor(RingBuffer<T> ringBuffer, Sequence[] sequences) {
        return new Batch2EventProcessor<T>(ringBuffer,sequences,eventHandler);
    }
}
