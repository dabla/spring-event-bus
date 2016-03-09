package be.dabla.bus;

import static be.dabla.bus.EventBus.eventBus;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

@Named
public class EventBusFactory {
	@Inject
	@Qualifier("taskExecutor")
	private ExecutorService executor;
	@Value("${thread.max.eventbus.size:25}")
    private int maxNumberOfThreads;
	
	EventBus create(String name, Collection<EventHandler> eventHandlers) {
		return eventBus(name, maxNumberOfThreads, executor, eventHandlers);
	}
}
