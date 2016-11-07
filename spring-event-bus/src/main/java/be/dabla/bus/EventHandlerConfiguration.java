package be.dabla.bus;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
@Inherited
public @interface EventHandlerConfiguration {
    String DEFAULT_CONTEXT = "default";
    String threadNamePattern();
    Class<? extends Manageable> managedBy() default Unmanaged.class;
    String context() default DEFAULT_CONTEXT;
}
