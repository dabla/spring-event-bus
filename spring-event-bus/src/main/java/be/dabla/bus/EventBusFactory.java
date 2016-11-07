package be.dabla.bus;

import static be.dabla.bus.EventBus.eventBus;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventBusFactory {
    @Autowired
    private ExecutorService executor;

    EventBus create(String name, int maxNumberOfThreads, Collection<EventHandler> eventHandlers) {
        return eventBus(name, maxNumberOfThreads, executor, eventHandlers);
    }
}
