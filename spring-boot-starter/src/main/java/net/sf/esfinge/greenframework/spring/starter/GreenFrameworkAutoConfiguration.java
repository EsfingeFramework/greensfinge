package net.sf.esfinge.greenframework.spring.starter;

import jakarta.annotation.PostConstruct;
import net.sf.esfinge.greenframework.spring.starter.properties.GreensfingeProperties;
import net.sf.esfinge.greenframework.spring.starter.processor.GreenifyBeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GreensfingeProperties.class)
@ComponentScan(basePackages = "net.sf.esfinge.greenframework.spring.starter.green")
public class GreenFrameworkAutoConfiguration {

    @PostConstruct
    public void init() {}

    @Bean
    public GreenifyBeanPostProcessor greenifyBeanPostProcessor() {
        return new GreenifyBeanPostProcessor();
    }
}
