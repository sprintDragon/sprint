package com.sprint.trace.distruptor;

import com.lmax.disruptor.dsl.Disruptor;

/**
 * Created by wangdi on 16-7-4.
 */
public interface IDisruptorFacade<T> {

    Disruptor<T> getDisruptor();

}
