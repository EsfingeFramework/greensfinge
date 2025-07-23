package net.sf.esfinge.greenframework.spring.starter.configuration;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "green.framework")
public class GreensfingeConfiguration {

    private boolean enable;
    private Double pricePerKwh;

    //This was necessary for the core module capture the environments correctly
    @PostConstruct
    public void postConstruct() {
        System.setProperty("green.framework.enable", String.valueOf(enable));
        System.setProperty("green.framework.price-per-kwh", String.valueOf(pricePerKwh));
    }
}
