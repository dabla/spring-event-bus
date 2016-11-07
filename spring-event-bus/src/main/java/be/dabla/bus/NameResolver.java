package be.dabla.bus;

import static be.dabla.bus.EventHandlerConfiguration.DEFAULT_CONTEXT;

class NameResolver {
    static final String EVENT_BUS = "EventBus";

    static NameResolver nameResolver() {
        return new NameResolver();
    }

    String resolve(EventHandler eventHandler) {
        return new StringBuilder(getContext(eventHandler)).append(EVENT_BUS)
                                                          .toString();
    }

    private String getContext(EventHandler eventHandler) {
        EventHandlerConfiguration annotation = eventHandler.getClass()
                .getAnnotation(EventHandlerConfiguration.class);

        if (annotation != null) {
            return annotation.context();
        }

        return DEFAULT_CONTEXT;
    }
}
