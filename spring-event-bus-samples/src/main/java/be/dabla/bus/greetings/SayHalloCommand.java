package be.dabla.bus.greetings;

import be.dabla.bus.Command;

@SuppressWarnings("serial")
public class SayHalloCommand implements Command {
	private final String name;

	private SayHalloCommand(String name) {
		this.name = name;
	}
	
	public static SayHalloCommand sayHalloCommand(String name) {
		return new SayHalloCommand(name);
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SayHalloCommand other = (SayHalloCommand) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name + " says hello!";
	}
}
