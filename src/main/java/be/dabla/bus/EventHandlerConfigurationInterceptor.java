package be.dabla.bus;

import static be.dabla.bus.Unmanaged.UNMANAGED;
import static be.dabla.spring.BeanLookup.getBean;
import static java.lang.Thread.currentThread;
import static org.slf4j.LoggerFactory.getLogger;

import java.text.MessageFormat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;

@Aspect
public class EventHandlerConfigurationInterceptor {
    private static final Logger LOGGER = getLogger(EventHandlerConfigurationInterceptor.class);

    @Around("execution(* *(..)) && @annotation(com.google.common.eventbus.Subscribe) && @within(EventHandlerConfiguration)")
    public Object handle(ProceedingJoinPoint jp) throws Throwable {
        final EventHandlerConfiguration annotation = getAnnotation(jp);
        final Manageable manageable = getManageable(annotation);

        if (manageable.isEnabled()) {
            final String threadName = currentThread().getName();

            try {
                final MessageFormat threadNamePattern = new MessageFormat(annotation.threadNamePattern());
                currentThread().setName(threadName(threadNamePattern));
                Object retVal = jp.proceed();
                return retVal;
            } catch(Throwable t) {
                throw t;
            } finally {
                currentThread().setName(threadName);
            }
        }
        else {
            LOGGER.warn("{} ignored event {} because {} was inactive",
                        jp.getTarget().getClass().getSimpleName(),
                        jp.getArgs()[0],
                        manageable.getClass().getSimpleName());
        }

        return null;
    }

    private Manageable getManageable(final EventHandlerConfiguration annotation) {
        if (UNMANAGED.getClass().equals(annotation.managedBy()) || getBean(annotation.managedBy()) == null) {
            return UNMANAGED;
        }

        return getBean(annotation.managedBy());
    }

    private String threadName(MessageFormat threadNamePattern) {
        return threadNamePattern.format(new Object[]{currentThread().getId()});
    }

    private EventHandlerConfiguration getAnnotation(ProceedingJoinPoint jp) {
        return jp.getTarget()
                 .getClass()
                 .getAnnotation(EventHandlerConfiguration.class);
    }
}
