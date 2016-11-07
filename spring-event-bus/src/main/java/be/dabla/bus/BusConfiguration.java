package be.dabla.bus;

import static be.dabla.bus.EventBusRegistry.eventBusRegistry;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Integer.parseInt;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@Configuration
@EnableSpringConfigured
@ComponentScan(basePackages={"be.dabla.bus"})
public class BusConfiguration {
    @Inject
    private DefaultListableBeanFactory beanFactory;
    @Inject
    private EventBusFactory eventBusFactory;
    
    @Bean
    @Inject
    EventBusRegistry registry(List<EventHandler> eventHandlers) {
    	return eventBusRegistry(eventHandlers);
    }

    @Bean
    @Inject
    List<EventBus> eventBuses(EventBusRegistry eventBusRegistry) {
    	List<EventBus> eventBuses = newArrayList();
    	
        for (String name : eventBusRegistry.getNames()) {
            int maxNumberOfThreads = getMaxNumberOfThreads(name);
            EventBus eventBus = eventBusFactory.create(name, maxNumberOfThreads, eventBusRegistry.getEventHandlers(name));
            eventBuses.add(eventBus);
            beanFactory.registerSingleton(name, eventBus);
        }
        
        return eventBuses;
    }
    
    private int getMaxNumberOfThreads(String name) {
		return parseInt(beanFactory.resolveEmbeddedValue("${thread.max." + name + ".size:25}"));
	}
}