package be.dabla.bus.greetings;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.mockito.Mockito.spy;

import java.util.concurrent.ExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@EnableSpringConfigured
@ComponentScan( basePackages = { "be.dabla.bus" } )
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