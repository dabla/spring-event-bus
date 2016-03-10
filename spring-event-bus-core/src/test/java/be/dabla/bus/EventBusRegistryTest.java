package be.dabla.bus;

import static be.dabla.bus.EventHandlerConfiguration.DEFAULT_CONTEXT;
import static be.dabla.bus.NameResolver.EVENT_BUS;
import static be.dabla.spring.BeanLookup.getBean;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.unitils.UnitilsBlockJUnit4ClassRunner;
import org.unitils.inject.annotation.InjectIntoByType;
import org.unitils.inject.annotation.TestedObject;

import be.dabla.spring.BeanLookup;
import be.dabla.test.AbstractTest;

import com.google.common.eventbus.Subscribe;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(UnitilsBlockJUnit4ClassRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(BeanLookup.class)
public class EventBusRegistryTest extends AbstractTest {
	private static final String DEFAULT_EVENT_BUS = DEFAULT_CONTEXT + EVENT_BUS;
	private static final String ANOTHER_EVENT_BUS = "another" + EVENT_BUS;
	
	@Mock
	private EventBus defaultEventBus, anotherEventBus;
	@Mock
	private Event event;
	@Mock
	private Command command;
	
	private AnEventHandler anEventHandler = new AnEventHandler();
	private ACommandHandler aCommandHandler = new ACommandHandler();
	
	@InjectIntoByType
	private List<EventHandler> eventHandlers = newArrayList(anEventHandler, aCommandHandler);
	
	@TestedObject
	private EventBusRegistry registry;
	
	@Before
	public void setUp() {
		mockStatic(BeanLookup.class);
        when(getBean(DEFAULT_EVENT_BUS, EventBus.class)).thenReturn(defaultEventBus);
        when(getBean(ANOTHER_EVENT_BUS, EventBus.class)).thenReturn(anotherEventBus);
	}
	
	@Test
	public void getNames() throws Exception {
		registry.create();
		
		assertThat(registry.getNames()).containsOnly(DEFAULT_EVENT_BUS, ANOTHER_EVENT_BUS);
	}
	
	@Test
	public void getEventHandlers() throws Exception {
		registry.create();
		
		assertThat(registry.getEventHandlers(DEFAULT_EVENT_BUS)).containsOnly(anEventHandler);
		assertThat(registry.getEventHandlers(ANOTHER_EVENT_BUS)).containsOnly(aCommandHandler);
	}
	
	private static class AnEventHandler implements EventHandler {
		@Subscribe
		public void handle(Event event) {
			
		}
	}
	
	@EventHandlerConfiguration(threadNamePattern="threadName-{0}",context="another")
	private static class ACommandHandler implements EventHandler {
		@Subscribe
		public void handle(Command command) {
			
		}
	}
}
