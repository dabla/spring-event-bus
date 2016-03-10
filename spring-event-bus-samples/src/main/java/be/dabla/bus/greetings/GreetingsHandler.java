package be.dabla.bus.greetings;

import static be.dabla.bus.EventPoster.anEventPoster;
import static be.dabla.bus.greetings.SaidHalloEvent.saidHalloEvent;
import be.dabla.bus.EventHandler;
import be.dabla.bus.EventHandlerConfiguration;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

@EventHandlerConfiguration(threadNamePattern="greetings-{0}")
public class GreetingsHandler implements EventHandler {
	@Subscribe
	@AllowConcurrentEvents
	public void handle(SayHalloCommand command) {
		System.out.println(command);
		
		anEventPoster().post(saidHalloEvent(command.getName()));
	}
	
	@Subscribe
	@AllowConcurrentEvents
	public void handle(SaidHalloEvent event) {
		System.out.println(event);
	}
}
