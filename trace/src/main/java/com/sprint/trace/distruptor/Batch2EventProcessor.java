package com.sprint.trace.distruptor;

/**
 * Created by stereo on 16-7-7.
 */
import com.lmax.disruptor.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public final class Batch2EventProcessor<T>
        implements EventProcessor
{

    public final static Logger logger = LoggerFactory.getLogger(Batch2EventProcessor.class);
    private final AtomicBoolean running = new AtomicBoolean(false);
    private ExceptionHandler exceptionHandler = new FatalExceptionHandler();
    private final DataProvider<T> dataProvider;
    private final SequenceBarrier sequenceBarrier;
    private final MultiEvenHandler<T> eventHandler;
    private final Sequence sequence = new Sequence(Sequencer.INITIAL_CURSOR_VALUE);

    public Batch2EventProcessor(
            final DataProvider<T> dataProvider,
            final Sequence[] sequences,
            final MultiEvenHandler<T> eventHandler)
    {
        this.dataProvider = dataProvider;
        this.sequenceBarrier = ((RingBuffer)this.dataProvider).newBarrier(sequences);
        this.eventHandler = eventHandler;
    }

    @Override
    public Sequence getSequence()
    {
        return sequence;
    }

    @Override
    public void halt()
    {
        running.set(false);
        sequenceBarrier.alert();
    }

    @Override
    public boolean isRunning()
    {
        return running.get();
    }

    public void setExceptionHandler(final ExceptionHandler exceptionHandler)
    {
        if (null == exceptionHandler)
        {
            throw new NullPointerException();
        }

        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void run()
    {
        if (!running.compareAndSet(false, true))
        {
            throw new IllegalStateException("Thread is already running");
        }
        sequenceBarrier.clearAlert();

        logger.info("Batch2EventProcessor start");
        T event = null;
        long nextSequence = sequence.get() + 1L;
        try
        {
            while (true)
            {
                try
                {
                    final long availableSequence = sequenceBarrier.waitFor(nextSequence);

                    List<T> events = new ArrayList<T>();
                    while (nextSequence <= availableSequence)
                    {
                        event = dataProvider.get(nextSequence);
                        events.add(event);
                        nextSequence++;
                    }
                    eventHandler.onEvent(events, nextSequence, nextSequence == availableSequence);
                    sequence.set(availableSequence);
                }
                catch (final AlertException ex)
                {
                    logger.error("Batch2EventProcessor ",ex);
                    if (!running.get())
                    {
                        break;
                    }
                }
                catch (final Throwable ex)
                {
                    logger.error("Batch2EventProcessor ",ex);
                    exceptionHandler.handleEventException(ex, nextSequence, event);
                    sequence.set(nextSequence);
                    nextSequence++;
                }
            }
        }
        finally
        {
            logger.info("Batch2EventProcessor shutdown");
            running.set(false);
        }
    }
}