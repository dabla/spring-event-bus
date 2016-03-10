package be.dabla.bus;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isSynchronizationActive;
import static org.springframework.transaction.support.TransactionSynchronizationManager.registerSynchronization;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;

import be.dabla.parallel.base.Callback;

@Configurable
public class EventPoster {
    @Inject
    private List<EventBus> eventBuses;

    private EventPoster() {}

    public static EventPoster anEventPoster() {
        return new EventPoster();
    }

    public <COMMAND extends Command> void post(COMMAND command) {
        for (EventBus eventBus : eventBuses) {
            eventBus.post(command);
        }
    }

    public <EVENT extends Event> void post(EVENT event) {
        for (EventBus eventBus : eventBuses) {
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
                            poster.execute();
                        }
                    }
                );
            return;
        }

        poster.execute();
    }
}
