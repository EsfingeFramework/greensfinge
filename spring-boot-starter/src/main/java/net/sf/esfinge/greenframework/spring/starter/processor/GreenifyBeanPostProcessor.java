package net.sf.esfinge.greenframework.spring.starter.processor;

import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.core.configuration.GreenFactory;
import net.sf.esfinge.greenframework.spring.starter.properties.GreensfingeProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class GreenifyBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private GreensfingeProperties greensfingeProperties;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(greensfingeProperties.isEnable() && (hasGreenAnnotations(bean.getClass()))) {
            return GreenFactory.greenify(bean);
        }

        return bean;
    }

    private boolean hasGreenAnnotations(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .anyMatch(f -> f.isAnnotationPresent(GreenConfigKey.class));
    }

}
