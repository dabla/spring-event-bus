package be.dabla.bus.greetings;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.mockito.Mockito.spy;

import java.util.concurrent.ExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import be.dabla.bus.BusConfiguration;

@Configuration
@EnableSpringConfigured
@Import({BusConfiguration.class})
public class GreetingsConfiguration {
	@Bean
	public ExecutorService executorService() {
		return newSingleThreadExecutor();
	}
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
	@Bean
	public GreetingsHandler greetingsHandler() {
		return spy(new GreetingsHandler());
	}
}