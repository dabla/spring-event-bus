package be.dabla.bus;

import static be.dabla.bus.AsyncEventBusWrapper.asyncEventBusWrapper;
import static be.dabla.concurrent.LimitedExecutorWrapper.limitedExecutorWrapper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.whenNew;

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
import org.unitils.inject.annotation.TestedObject;

import com.google.common.eventbus.AsyncEventBus;

import be.dabla.concurrent.LimitedExecutorWrapper;
import be.dabla.test.AbstractTest;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(UnitilsBlockJUnit4ClassRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest({LimitedExecutorWrapper.class, AsyncEventBusWrapper.class})
public class AsyncEventBusWrapperTest extends AbstractTest {
	private static final int MAX_NUMBER_OF_THREADS = 10;
	
	@Mock
	private ExecutorService executor;
	@Mock
	private LimitedExecutorWrapper limitedExecutorWrapper;
	@Mock
	private AsyncEventBus eventBus;
	@Mock
	private Object object;
	
	@TestedObject
	private AsyncEventBusWrapper wrapper;
	
	@Before
    public void setUp() throws Exception {
        mockStatic(LimitedExecutorWrapper.class);
        when(limitedExecutorWrapper(executor, MAX_NUMBER_OF_THREADS)).thenReturn(limitedExecutorWrapper);
        when(limitedExecutorWrapper.getMaxNumberOfThreads()).thenReturn(MAX_NUMBER_OF_THREADS);
        whenNew(AsyncEventBus.class).withArguments(limitedExecutorWrapper).thenReturn(eventBus);
        
        wrapper = asyncEventBusWrapper(executor, MAX_NUMBER_OF_THREADS);
    }
	
	@Test
	public void post() {
		wrapper.post(object);
		
		verify(eventBus).post(object);
    }
    
	@Test
	public void register() {
		wrapper.register(object);
		
		verify(eventBus).register(object);
    }
    
	@Test
	public void shutdown() {
		wrapper.shutdown();
		
		verify(limitedExecutorWrapper).shutdownNow();
    }
	
	@Test
	public void getMaxNumberOfThreads() {
		assertThat(wrapper.getMaxNumberOfThreads()).isEqualTo(MAX_NUMBER_OF_THREADS);
	}
	
	@Test
	public void setMaxNumberOfThreads() {
		wrapper.setMaxNumberOfThreads(MAX_NUMBER_OF_THREADS);
		
		verify(limitedExecutorWrapper).setMaxNumberOfThreads(MAX_NUMBER_OF_THREADS);
	}
	
	@Test
	public void isIdle_whenNumberOfThreadsIsZero_thenReturnTrue() {
		assertThat(wrapper.isIdle()).isTrue();
	}
	
	@Test
	public void isIdle_whenNumberOfThreadsIsNotZero_thenReturnFalse() {
		when(limitedExecutorWrapper.getNumberOfThreads()).thenReturn(MAX_NUMBER_OF_THREADS);
		assertThat(wrapper.isIdle()).isFalse();
	}
}
