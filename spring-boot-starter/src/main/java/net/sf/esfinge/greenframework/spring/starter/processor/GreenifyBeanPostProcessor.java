package net.sf.esfinge.greenframework.spring.starter.processor;

import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.core.configuration.GreenFactory;
import net.sf.esfinge.greenframework.spring.starter.properties.GreenFrameworkProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class GreenifyBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private GreenFrameworkProperties greenFrameworkProperties;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(greenFrameworkProperties.isEnable() && (hasGreenAnnotations(bean.getClass()))) {
            return GreenFactory.greenify(bean);
        }

        return bean;
    }

    private boolean hasGreenAnnotations(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .anyMatch(f -> f.isAnnotationPresent(GreenConfigKey.class)) ||
                Arrays.stream(clazz.getDeclaredMethods())
                        .anyMatch(m -> m.isAnnotationPresent(GreenConfigKey.class));
    }


}
