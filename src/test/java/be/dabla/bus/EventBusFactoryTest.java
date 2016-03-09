package be.dabla.bus;

import static be.dabla.bus.EventBus.eventBus;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.concurrent.ExecutorService;

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

import be.dabla.AbstractTest;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(UnitilsBlockJUnit4ClassRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(EventBus.class)
public class EventBusFactoryTest extends AbstractTest {
	private static final int MAX_NUMBER_OF_THREADS = 25;
	private static final String NAME = "name";
	
	@Mock
	private EventHandler eventHandler;
	@Mock
	private EventBus eventBus;
	
	@Mock
	@InjectIntoByType
	private ExecutorService executor;
	@InjectIntoByType
    private int maxNumberOfThreads = MAX_NUMBER_OF_THREADS;
	
	@TestedObject
	private EventBusFactory factory;
	
	@Before
	public void setUp() {
		mockStatic(EventBus.class);
		when(eventBus(NAME, MAX_NUMBER_OF_THREADS, executor, newArrayList(eventHandler))).thenReturn(eventBus);
	}
	
	@Test
	public void create() throws Exception {
		assertThat(factory.create(NAME, newArrayList(eventHandler))).isEqualTo(eventBus);
	}
}
