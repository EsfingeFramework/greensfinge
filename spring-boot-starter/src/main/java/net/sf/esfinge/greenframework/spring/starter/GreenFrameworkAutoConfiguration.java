package net.sf.esfinge.greenframework.spring.starter;

import net.sf.esfinge.greenframework.spring.starter.properties.GreenFrameworkProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GreenFrameworkProperties.class)
@ComponentScan(basePackages = "net.sf.esfinge.greenframework.spring.starter")
public class GreenFrameworkAutoConfiguration {

}
