package net.sf.esfinge.greenframework.spring.starter.processor;

import net.sf.esfinge.greenframework.core.configuration.GreenFactory;
import net.sf.esfinge.greenframework.spring.starter.properties.GreenFrameworkProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreenifyBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private GreenFrameworkProperties greenFrameworkProperties;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(greenFrameworkProperties.isEnable()) {
            return GreenFactory.greenify(bean);
        }

        return bean;
    }
}
