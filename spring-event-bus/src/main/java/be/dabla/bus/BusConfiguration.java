package be.dabla.bus;

import static be.dabla.bus.EventBusRegistry.eventBusRegistry;

import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@Configuration
@EnableSpringConfigured
@ComponentScan(basePackages={"be.dabla.bus"})
public class BusConfiguration {
    @Bean
    @Inject
    EventBusRegistry registry(List<EventHandler> eventHandlers) {
    	return eventBusRegistry(eventHandlers);
    }

    @Bean
    @Inject
    List<EventBus> eventBuses(EventBusRegistry eventBusRegistry) {
    	return new EventBusFactoryPostProcessor().create(eventBusRegistry);
    }
}