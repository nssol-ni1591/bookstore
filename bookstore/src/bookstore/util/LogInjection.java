package bookstore.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import bookstore.annotation.Log;

public class LogInjection implements BeanPostProcessor {

	@Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, String name) {
    	ReflectionUtils.doWithFields(bean.getClass(), field -> {
    		// lambda: new ReflectionUtils.FieldCallback() { ... } -
    		// make the field accessible if defined private
    		ReflectionUtils.makeAccessible(field);
    		if (field.getAnnotation(Log.class) != null) {
    			Logger log = Logger.getLogger(bean.getClass().toString());
    			try {
    				field.set(bean, log);
    			}
    			catch (IllegalArgumentException | IllegalAccessException e) {
    				log.log(Level.SEVERE, "In setting @Log", e);
                }
            }
        });
        return bean;
    }
}
