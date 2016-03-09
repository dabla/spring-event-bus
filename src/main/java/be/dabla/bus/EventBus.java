package be.dabla.bus;

import static be.dabla.bus.AsyncEventBusWrapper.asyncEventBusWrapper;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.springframework.beans.factory.DisposableBean;

public class EventBus implements DisposableBean {
    private static final Logger LOGGER = getLogger(EventBus.class);

    private final String name;
    private final AsyncEventBusWrapper eventBus;
    private final AsyncEventBusWrapper commandBus;

    private EventBus(String name,
                     AsyncEventBusWrapper eventBus,
                     AsyncEventBusWrapper commandBus) {
        this.name = name;
        this.eventBus = eventBus;
        this.commandBus = commandBus;
    }

    static EventBus eventBus(String name, int maxNumberOfThreads, ExecutorService executor, Collection<EventHandler> eventHandlers) {
        EventBus eventBus = new EventBus(name,
                                         asyncEventBusWrapper(executor, maxNumberOfThreads),
                                         asyncEventBusWrapper(executor, maxNumberOfThreads));

        for (EventHandler eventHandler : eventHandlers) {
            eventBus.register(eventHandler);
        }

        return eventBus;
    }

    void register(EventHandler eventHandler) {
        eventBus.register(eventHandler);
        commandBus.register(eventHandler);
        LOGGER.info("Registered {} on {}", eventHandler.getClass().getName(), name);
    }

    public void post(Event event) {
        eventBus.post(event);
    }

    public void post(Command event) {
        commandBus.post(event);
    }

    public AsyncEventBusWrapper eventBus() {
        return eventBus;
    }

    public AsyncEventBusWrapper commandBus() {
        return commandBus;
    }

    @Override
    public void destroy() throws Exception {
        eventBus.shutdown();
        commandBus.shutdown();
    }

    public String getName() {
        return name;
    }
}