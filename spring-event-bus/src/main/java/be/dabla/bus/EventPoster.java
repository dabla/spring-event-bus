package be.dabla.bus;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.transaction.support.TransactionSynchronizationManager.isSynchronizationActive;
import static org.springframework.transaction.support.TransactionSynchronizationManager.registerSynchronization;

import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;

import be.dabla.parallel.base.Callback;

@Configurable
public class EventPoster {
    private static final Logger LOGGER = getLogger(EventPoster.class);
    @Autowired
    private List<EventBus> eventBuses;

    private EventPoster() {}

    public static EventPoster anEventPoster() {
        return new EventPoster();
    }

    public <COMMAND extends Command> void post(COMMAND command) {
        for (EventBus eventBus : eventBuses()) {
            eventBus.post(command);
        }
    }

    public <EVENT extends Event> void post(EVENT event) {
        for (EventBus eventBus : eventBuses()) {
            eventBus.post(event);
        }
    }

    public <EVENT extends Event> void postAfterCommit(final EVENT event) {
        postAfterCommit(new Callback<Void>() {
            @Override
            public Void execute() {
                post(event);
                return null;
            }
        });
    }

    public <COMMAND extends Command> void postAfterCommit(final COMMAND command) {
        postAfterCommit(new Callback<Void>() {
            @Override
            public Void execute() {
                post(command);
                return null;
            }
        });
    }

    private static void postAfterCommit(final Callback<Void> poster) {
        if (isSynchronizationActive()) {
            registerSynchronization(
                    new TransactionSynchronizationAdapter() {
                        @Override
                        public void afterCommit() {
                            LOGGER.info("postAfterCommit : poster.execute");
                            poster.execute();
                        }
                    }
                );
            return;
        }
        LOGGER.info("postAfterCommit : Synchronization is not Active : poster.execute");
        poster.execute();
    }
    
    private List<EventBus> eventBuses() {
    	return eventBuses != null ? eventBuses : Collections.<EventBus>emptyList();
    }
} 
