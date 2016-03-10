package be.dabla.bus.greetings;

import static be.dabla.bus.EventPoster.anEventPoster;
import static be.dabla.bus.greetings.SayHalloCommand.sayHalloCommand;

import javax.inject.Named;

@Named
public class Greeter {
	public void sayHallo(String name) {
		anEventPoster().post(sayHalloCommand(name));
	}
}
