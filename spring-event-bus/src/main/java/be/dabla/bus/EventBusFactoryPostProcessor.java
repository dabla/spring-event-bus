package be.dabla.bus;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Integer.parseInt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.google.common.base.Function;

@Configurable
class EventBusFactoryPostProcessor {
    @Autowired
    private DefaultListableBeanFactory beanFactory;
    @Autowired
    private EventBusFactory eventBusFactory;

    public List<EventBus> create(EventBusRegistry eventBusRegistry) {
    	return newArrayList(from(eventBusRegistry.getNames()).transform(toEventBus(eventBusRegistry)));
    }
    
    private Function<String, EventBus> toEventBus(final EventBusRegistry eventBusRegistry) {
		return new Function<String, EventBus>() {
			@Override
			public EventBus apply(String name) {
				int maxNumberOfThreads = getMaxNumberOfThreads(name);
	            EventBus eventBus = eventBusFactory.create(name, maxNumberOfThreads, eventBusRegistry.getEventHandlers(name));
	            beanFactory.registerSingleton(name, eventBus);
	            return eventBus;
			}
		};
	}

    public void destroy() {
    	for (EventBus eventBus : beanFactory.getBeansOfType(EventBus.class).values()) {
    		eventBus.destroy();
    	}
    }
    
    private int getMaxNumberOfThreads(String name) {
		return parseInt(beanFactory.resolveEmbeddedValue("${thread.max." + name + ".size:25}"));
	}
}
