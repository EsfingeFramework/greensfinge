package net.sf.esfinge.greenframework.spring.starter;

import jakarta.annotation.PostConstruct;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenCustomMockFacade;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenMetricFacade;
import net.sf.esfinge.greenframework.spring.starter.properties.GreenFrameworkProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GreenFrameworkProperties.class)
@ComponentScan(basePackages = "net.sf.esfinge.greenframework.spring.starter")
public class GreenFrameworkAutoConfiguration {

    @PostConstruct
    public void init() {
        // Post Constructor
    }

    @Bean
    public GreenConfigurationFacade greenConfigurationFacade() {
        return new GreenConfigurationFacade();
    }

    @Bean
    public GreenMetricFacade greenMetricFacade() {
        return new GreenMetricFacade();
    }

    @Bean
    public GreenCustomMockFacade greenCustomConfigurationFacade() {
        return new GreenCustomMockFacade();
    }
}
