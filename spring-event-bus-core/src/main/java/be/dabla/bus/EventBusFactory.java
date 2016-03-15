package be.dabla.bus;

import static be.dabla.bus.EventBus.eventBus;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class EventBusFactory {
	@Inject
	private ExecutorService executor;
		
	EventBus create(String name, int maxNumberOfThreads, Collection<EventHandler> eventHandlers) {
		return eventBus(name, maxNumberOfThreads, executor, eventHandlers);
	}
}
