package be.dabla.bus;

import static be.dabla.concurrent.LimitedExecutorWrapper.limitedExecutorWrapper;

import java.util.concurrent.ExecutorService;

import com.google.common.eventbus.AsyncEventBus;

import be.dabla.concurrent.LimitedExecutorWrapper;

public class AsyncEventBusWrapper {
    private final LimitedExecutorWrapper limitedExecutorWrapper;
    private final AsyncEventBus eventBus;

    private AsyncEventBusWrapper(LimitedExecutorWrapper limitedExecutorWrapper, AsyncEventBus eventBus) {
        this.limitedExecutorWrapper = limitedExecutorWrapper;
        this.eventBus = eventBus;
    }

    static AsyncEventBusWrapper asyncEventBusWrapper(ExecutorService executor, int maxNumberOfThreads) {
        LimitedExecutorWrapper limitedExecutorWrapper = limitedExecutorWrapper(executor, maxNumberOfThreads);
        return new AsyncEventBusWrapper(limitedExecutorWrapper, new com.google.common.eventbus.AsyncEventBus(limitedExecutorWrapper));
    }

    public int getMaxNumberOfThreads() {
        return limitedExecutorWrapper.getMaxNumberOfThreads();
    }

    public void setMaxNumberOfThreads(int maxNumberOfThreads) {
        limitedExecutorWrapper.setMaxNumberOfThreads(maxNumberOfThreads);
    }

    public int getNumberOfThreads() {
        return limitedExecutorWrapper.getNumberOfThreads();
    }

    void post(Object event) {
        eventBus.post(event);
    }

    void register(Object object) {
        eventBus.register(object);
    }

    void shutdown() {
        limitedExecutorWrapper.shutdownNow();
    }
    
    boolean isIdle() {
    	return getNumberOfThreads() == 0;
    }
}
