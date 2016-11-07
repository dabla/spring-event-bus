package be.dabla.bus.greetings;

import static be.dabla.asserters.Poller.aPoller;
import static be.dabla.bus.greetings.SaidHalloEvent.saidHalloEvent;
import static be.dabla.bus.greetings.SayHalloCommand.sayHalloCommand;
import static org.mockito.Mockito.inOrder;

import javax.inject.Inject;

import org.junit.Test;
import org.mockito.InOrder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import be.dabla.asserters.Assertion;

@ContextConfiguration(classes={GreetingsConfiguration.class})
public class GreetingsHandlerIntegrationTest extends AbstractJUnit4SpringContextTests {
	private static final String NAME = "Zona le Jar";
	@Inject
	private Greeter greeter;
	@Inject
	private GreetingsHandler greetingsHandler;
	
	@Test
	public void handle() throws Exception {
		greeter.sayHallo(NAME);
		
		aPoller().doAssert(new Assertion() {
			@Override
			public void assertion() throws Exception {
				InOrder inOrder = inOrder(greetingsHandler);
				inOrder.verify(greetingsHandler).handle(sayHalloCommand(NAME));
				inOrder.verify(greetingsHandler).handle(saidHalloEvent(NAME));
			}
		});
	}
}
