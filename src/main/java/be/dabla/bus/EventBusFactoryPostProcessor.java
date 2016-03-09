package be.dabla.bus;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;

@Named	
class EventBusFactoryPostProcessor {
	@Inject
	private DefaultListableBeanFactory beanFactory;
	@Inject
	private EventBusRegistry eventBusRegistry;
	@Inject
	private EventBusFactory eventBusFactory;

	@PostConstruct
	public void create() {
		for (String name : eventBusRegistry.getNames()) {
			if (!beanFactory.containsBean(name)) {
				EventBus eventBus = eventBusFactory.create(name, eventBusRegistry.getEventHandlers(name));
				beanFactory.registerSingleton(name, eventBus);
			}
		}
	}
}
