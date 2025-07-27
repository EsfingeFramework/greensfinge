package net.sf.esfinge.greenframework.spring.starter.properties;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Role;

@Getter
@Setter
@ConfigurationProperties(prefix = "green.framework")
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class GreenFrameworkProperties {

    private boolean enable;
    private Double pricePerKwh;

    //This was necessary for the core module capture the environments correctly
    @PostConstruct
    public void postConstruct() {
        System.setProperty("green.framework.enable", String.valueOf(enable));
        System.setProperty("green.framework.price-per-kwh", String.valueOf(pricePerKwh));
    }
}
