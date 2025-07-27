package net.sf.esfinge.greenframework.spring.starter;

import jakarta.annotation.PostConstruct;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenMetricFacade;
import net.sf.esfinge.greenframework.spring.starter.properties.GreenFrameworkProperties;
import net.sf.esfinge.greenframework.spring.starter.processor.GreenifyBeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GreenFrameworkProperties.class)
@ComponentScan(basePackages = "net.sf.esfinge.greenframework.spring.starter.green")
public class GreenFrameworkAutoConfiguration {

    @PostConstruct
    public void init() {}

    @Bean
    public GreenifyBeanPostProcessor greenifyBeanPostProcessor() {
        return new GreenifyBeanPostProcessor();
    }

    @Bean
    public GreenConfigurationFacade greenConfigurationFacade() {
        return new GreenConfigurationFacade();
    }

    @Bean
    public GreenMetricFacade greenMetricFacade() {
        return new GreenMetricFacade();
    }
}
