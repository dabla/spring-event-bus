package be.dabla.bus;

import static be.dabla.bus.NameResolver.nameResolver;
import static com.google.common.collect.Multimaps.index;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;

public class EventBusRegistry {
    private final Map<String, Collection<EventHandler>> managedBy;

    private EventBusRegistry(Map<String, Collection<EventHandler>> managedBy) {
		this.managedBy = managedBy;
    }

    static EventBusRegistry eventBusRegistry(List<EventHandler> eventHandlers) {
        return new EventBusRegistry(index(eventHandlers, managedBy()).asMap());
    }

    public Set<String> getNames() {
        return managedBy.keySet();
    }

    public Collection<EventHandler> getEventHandlers(String name) {
        return managedBy.get(name);
    }

    private static Function<EventHandler, String> managedBy() {
        return new Function<EventHandler, String>() {
            @Override
            public String apply(EventHandler eventHandler) {
                return nameResolver().resolve(eventHandler);
            }
        };
    }
}
