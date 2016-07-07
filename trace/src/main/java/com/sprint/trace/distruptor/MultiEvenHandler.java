package com.sprint.trace.distruptor;

import com.lmax.disruptor.EventHandler;

import java.util.List;

/**
 * Created by stereo on 16-7-7.
 */
public interface MultiEvenHandler<T> extends EventHandler<T> {

    void onEvent(List<T> var1, long var2, boolean var4) throws Exception;
}
