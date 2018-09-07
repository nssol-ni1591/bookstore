package bookstore.util;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import bookstore.annotation.Log;

public class LogInjection implements BeanPostProcessor {

	@Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
		//System.out.println("LogInjection.postProcessAfterInitialization: beanname=" + beanName)
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(final Object bean, String name) {
  
    	ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {

    		@Override
    		public void doWith(Field field) {
                // make the field accessible if defined private
                ReflectionUtils.makeAccessible(field);
                if (field.getAnnotation(Log.class) != null) {
        			//System.out.println("LogInjection.postProcessBeforeInitialization.doWith: field=" + field.getName() + ", bean=" + bean.toString())
                    Logger log = Logger.getLogger(bean.getClass().toString());
                    try {
						field.set(bean, log);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
                }
            }
        });
        return bean;
    }
}
