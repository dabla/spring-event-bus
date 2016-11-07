package be.dabla.bus;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.Serializable;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;


@Component
public class EventLoggingHandler implements EventHandler {

    private static final Logger LOGGER = getLogger(EventLoggingHandler.class);
    
    @Subscribe
    @AllowConcurrentEvents
    public <EVENT extends Serializable> void handle(EVENT event) {
        LOGGER.info("Processing {}", event);
    }
}