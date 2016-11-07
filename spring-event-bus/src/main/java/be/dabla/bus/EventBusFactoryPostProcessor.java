package be.dabla.bus;

import static java.lang.Integer.parseInt;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
class EventBusFactoryPostProcessor {
    @Autowired
    private DefaultListableBeanFactory beanFactory;
    @Autowired
    private EventBusRegistry eventBusRegistry;
    @Autowired
    private EventBusFactory eventBusFactory;

    @PostConstruct
    public void create() {
        for (String name : eventBusRegistry.getNames()) {
            if (!beanFactory.containsBean(name)) {
            	int maxNumberOfThreads = getMaxNumberOfThreads(name);
                EventBus eventBus = eventBusFactory.create(name, maxNumberOfThreads, eventBusRegistry.getEventHandlers(name));
                beanFactory.registerSingleton(name, eventBus);
            }
        }
    }
    
    @PreDestroy
    public void destroy() {
    	for (EventBus eventBus : beanFactory.getBeansOfType(EventBus.class).values()) {
    		eventBus.destroy();
    	}
    }
    
    private int getMaxNumberOfThreads(String name) {
		return parseInt(beanFactory.resolveEmbeddedValue("${thread.max." + name + ".size:25}"));
	}
}
