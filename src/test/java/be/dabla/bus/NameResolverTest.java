package be.dabla.bus;

import static be.dabla.bus.NameResolver.nameResolver;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import be.dabla.bus.Command;
import be.dabla.bus.Event;
import be.dabla.bus.EventHandler;
import be.dabla.bus.EventHandlerConfiguration;

import com.google.common.eventbus.Subscribe;


public class NameResolverTest {
	@Test
	public void resolve() throws Exception {
		assertThat(nameResolver().resolve(new AnEventHandler())).isEqualTo("defaultEventBus");
		assertThat(nameResolver().resolve(new ACommandHandler())).isEqualTo("anotherEventBus");
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
