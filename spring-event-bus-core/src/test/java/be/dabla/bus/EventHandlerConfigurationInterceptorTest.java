package be.dabla.bus;

import static be.dabla.spring.BeanLookup.getBean;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.unitils.UnitilsBlockJUnit4ClassRunner;
import org.unitils.inject.annotation.TestedObject;

import be.dabla.spring.BeanLookup;
import be.dabla.test.AbstractTest;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(UnitilsBlockJUnit4ClassRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(BeanLookup.class)
public class EventHandlerConfigurationInterceptorTest extends AbstractTest {
	@Mock
	private Manageable manageable;
	@Mock
	private ProceedingJoinPoint jointPoint;
	@Mock
	private Object event;
	
	@TestedObject
	private EventHandlerConfigurationInterceptor interceptor;
	
	@Before
	@SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        mockStatic(BeanLookup.class);
        when(getBean(Manageable.class)).thenReturn(manageable);
        when(getBean(Manageable.class)).thenReturn(manageable);
        when(getBean(Unmanaged.class)).thenThrow(NoSuchBeanDefinitionException.class);
        when(jointPoint.getTarget()).thenReturn(new ManagedHandler());
        when(jointPoint.getArgs()).thenReturn(new Object[]{event});
    }
	
	@Test
	public void handle_whenManagedHandlerIsEnabled() throws Throwable {
		when(manageable.isEnabled()).thenReturn(true);
		
		interceptor.handle(jointPoint);
	}
	
	@Test
	public void handle_whenManagedHandlerIsDisabled() throws Throwable {
		interceptor.handle(jointPoint);
	}
	
	@Test
	public void handle_whenUnmanagedHandler() throws Throwable {
		when(jointPoint.getTarget()).thenReturn(new UnmanagedHandler());
		
		interceptor.handle(jointPoint);
	}
	
	@EventHandlerConfiguration(threadNamePattern="threadNamePattern-{0}",managedBy=Manageable.class)
	private static class ManagedHandler {
		
	}
	
	@EventHandlerConfiguration(threadNamePattern="threadNamePattern-{0}")
	private static class UnmanagedHandler {
		
	}
}
