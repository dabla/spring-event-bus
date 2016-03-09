package be.dabla.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;

import be.dabla.AbstractTest;
import be.dabla.spring.BeanLookup;


public class BeanLookupTest extends AbstractTest {
	private static final String NAME = "propertyPlaceholderConfigurer";
	
	@Mock
	private ApplicationContext context;
	@Mock
	private PropertyPlaceholderConfigurer placeholder, namedPlaceholder;
	
	@Before
	public void setUp() throws Exception {
		when(context.getBean(PropertyPlaceholderConfigurer.class)).thenReturn(placeholder);
		when(context.getBean(NAME, PropertyPlaceholderConfigurer.class)).thenReturn(namedPlaceholder);
	}
	
	@Test
	public void getBean_whenContextPresent() throws Exception {
		new BeanLookup().setApplicationContext(context);
		
		assertThat(BeanLookup.getBean(BeanLookup.class)).isNull();
		assertThat(BeanLookup.getBean(PropertyPlaceholderConfigurer.class)).isEqualTo(placeholder);
		assertThat(BeanLookup.getBean(NAME, PropertyPlaceholderConfigurer.class)).isEqualTo(namedPlaceholder);
	}
	
	@Test
	public void getBean_whenContextNotPresent() throws Exception {
		new BeanLookup().setApplicationContext(null);
		
		assertThat(BeanLookup.getBean(BeanLookup.class)).isNull();
		assertThat(BeanLookup.getBean(PropertyPlaceholderConfigurer.class)).isNull();
		assertThat(BeanLookup.getBean(NAME, PropertyPlaceholderConfigurer.class)).isNull();
	}
}
