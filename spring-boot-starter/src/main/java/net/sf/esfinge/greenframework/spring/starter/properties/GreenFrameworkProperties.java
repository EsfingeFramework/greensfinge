package net.sf.esfinge.greenframework.spring.starter.properties;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Role;

import java.util.Objects;

import static net.sf.esfinge.greenframework.core.util.StringUtil.toStringOrNull;

@Getter
@Setter
@ConfigurationProperties(prefix = "green.framework")
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class GreenFrameworkProperties {

    private boolean enable;
    private Double pricePerKwh;
    private Double co2PerKmCar;
    private Double co2AbsorbedPerTreeYear;
    private Double powerLampLedWatts;

    //This was necessary for the core module capture the environments correctly
    @PostConstruct
    public void postConstruct() {
        setProperty("green.framework.enable", toStringOrNull(enable));
        setProperty("green.framework.price-per-kwh", toStringOrNull(pricePerKwh));
        setProperty("green.framework.co2-per-km-car", toStringOrNull(co2PerKmCar));
        setProperty("green.framework.co2-absorbed-per-tree-year", toStringOrNull(co2AbsorbedPerTreeYear));
        setProperty("green.framework.power-lamp-led-watts", toStringOrNull(powerLampLedWatts));
    }

    private void setProperty(String key, String value) {
        if(Objects.nonNull(value)) {
            System.setProperty(key, value);
        }
    }
}
