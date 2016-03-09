package be.dabla.bus;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.Serializable;

import javax.inject.Named;

import org.slf4j.Logger;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;


@Named
public class EventLoggingHandler implements EventHandler {
    private static final Logger LOGGER = getLogger(EventLoggingHandler.class);
    
    @Subscribe
    @AllowConcurrentEvents
    public <EVENT extends Serializable> void handle(EVENT event) {
        LOGGER.info("Processing {}", event);
    }
}