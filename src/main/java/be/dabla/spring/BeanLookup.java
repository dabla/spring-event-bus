package be.dabla.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeanLookup implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        BeanLookup.context = context;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context == null ? null : (T) context.getBean(beanClass);
    }
    
    public static <T> T getBean(String id, Class<T> beanClass) {
        return context == null ? null : (T) context.getBean(id, beanClass);
    }
    
}