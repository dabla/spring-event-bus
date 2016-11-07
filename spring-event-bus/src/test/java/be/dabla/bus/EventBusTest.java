package be.dabla.bus;

import static be.dabla.bus.AsyncEventBusWrapper.asyncEventBusWrapper;
import static be.dabla.bus.EventBus.eventBus;
import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.concurrent.ExecutorService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.unitils.UnitilsBlockJUnit4ClassRunner;
import org.unitils.inject.annotation.TestedObject;

import be.dabla.test.AbstractTest;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(UnitilsBlockJUnit4ClassRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(AsyncEventBusWrapper.class)
public class EventBusTest extends AbstractTest {
	private static final int MAX_NUMBER_OF_THREADS = 25;
	private static final String NAME = "name";
	
	@Mock
	private ExecutorService executor;
	@Mock
	private AsyncEventBusWrapper asyncEventBus, asyncCommandBus;
	@Mock
	private EventHandler eventHandler;
	@Mock
	private Event event;
	@Mock
	private Command command;
	
	@TestedObject
	private EventBus eventBus;

	@Before
	public void setUp() {
		mockStatic(AsyncEventBusWrapper.class);
		when(asyncEventBusWrapper(executor, MAX_NUMBER_OF_THREADS)).thenReturn(asyncEventBus, asyncCommandBus);
		when(asyncEventBus.isIdle()).thenReturn(true);
		when(asyncCommandBus.isIdle()).thenReturn(true);
		eventBus = eventBus(NAME, MAX_NUMBER_OF_THREADS, executor, newArrayList(eventHandler));
	}
	
	@Test
	public void register() throws Exception {
		InOrder inOrder = inOrder(asyncEventBus, asyncCommandBus);
		inOrder.verify(asyncEventBus).register(eventHandler);
		inOrder.verify(asyncCommandBus).register(eventHandler);
	}
	
	@Test
	public void post_whenEvent() throws Exception {
		eventBus.post(event);
		
		verify(asyncEventBus).post(event);
	}
	
	@Test
	public void post_whenCommand() throws Exception {
		eventBus.post(command);
		
		verify(asyncCommandBus).post(command);
	}
	
	@Test
	public void eventBus_thenReturnAsyncEventBus() throws Exception {
		assertThat(eventBus.eventBus()).isEqualTo(asyncEventBus);
	}
	
	@Test
	public void commandBus_thenReturnAsyncEventBus() throws Exception {
		assertThat(eventBus.commandBus()).isEqualTo(asyncCommandBus);
	}
	
	@Test
	public void destroy() throws Exception {
		eventBus.destroy();
		
		InOrder inOrder = inOrder(asyncEventBus, asyncCommandBus);
		inOrder.verify(asyncEventBus).shutdown();
		inOrder.verify(asyncCommandBus).shutdown();
	}
	
	@Test
	public void getName() throws Exception {
		assertThat(eventBus.getName()).isEqualTo(NAME);
	}
	
	@Test
	public void isIdle_whenAsyncEventBusAndAsyncCommandBusIsIdle_thenReturnTrue() throws Exception {
		assertThat(eventBus.isIdle()).isTrue();
	}
	
	@Test
	public void isIdle_whenAsyncEventBusIsIdleAndAsyncCommandBusIsBusy_thenReturnTrue() throws Exception {
		when(asyncCommandBus.isIdle()).thenReturn(false);
		assertThat(eventBus.isIdle()).isFalse();
	}
	
	@Test
	public void isIdle_whenAsyncEventBusIsBusyAndAsyncCommandBusIsIdle_thenReturnTrue() throws Exception {
		when(asyncEventBus.isIdle()).thenReturn(false);
		assertThat(eventBus.isIdle()).isFalse();
	}
}
