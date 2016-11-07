package be.dabla.bus;

import static be.dabla.bus.EventPoster.anEventPoster;
import static com.google.common.collect.Lists.newArrayList;
import static org.fest.reflect.core.Reflection.field;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.unitils.inject.annotation.TestedObject;

import be.dabla.test.AbstractTest;


public class EventPosterTest extends AbstractTest {
	@Mock
    private Event event;
    @Mock
    private Command command;
    @Mock
    private EventBus eventBus, anotherEventBus;
    
    @TestedObject
    private EventPoster eventPoster = anEventPoster();
    
    @Before
    public void setUp() {
    	field("eventBuses").ofType(List.class).in(eventPoster).set(newArrayList(eventBus, anotherEventBus));
	}
    
    @Test
    public void post_whenEvent() throws Exception {
        eventPoster.post(event);
        
        verify(eventBus).post(event);
        verify(anotherEventBus).post(event);
    }
    
    @Test
    public void post_whenEventButNoEventBusesPresent() throws Exception {
    	field("eventBuses").ofType(List.class).in(eventPoster).set(null);
    	
    	eventPoster.post(event);
    	
    	verifyZeroInteractions(eventBus, anotherEventBus);
    }
    
    @Test
    public void postAfterCommit_whenEvent() throws Exception {
        eventPoster.postAfterCommit(event);
        
        verify(eventBus).post(event);
        verify(anotherEventBus).post(event);
    }

    @Test
    public void post_whenCommand() throws Exception {
        eventPoster.post(command);
        
        verify(eventBus).post(command);
        verify(anotherEventBus).post(command);
    }
    
    @Test
    public void post_whenCommandButNoEventBusesPresent() throws Exception {
    	field("eventBuses").ofType(List.class).in(eventPoster).set(null);
    	
    	eventPoster.post(command);
    	
    	verifyZeroInteractions(eventBus, anotherEventBus);
    }
    
    @Test
    public void postAfterCommit_whenCommand() throws Exception {
        eventPoster.postAfterCommit(command);
        
        verify(eventBus).post(command);
        verify(anotherEventBus).post(command);
    }
}
