package net.sf.esfinge.greenframework.spring.starter.config;

import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenCustomMockFacade;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenMetricFacade;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenScanMappingFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreenFrameworkConfiguration {

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

    @Bean
    public GreenScanMappingFacade greenScanMappingFacade() {
        return new GreenScanMappingFacade();
    }
}
