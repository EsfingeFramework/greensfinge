package br.com.ita.greenframework.configuration;

import br.com.ita.greenframework.dto.annotation.GreenDefaultConfiguration;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GreenThreadLocal {

    private static final ThreadLocal<Map<String, GreenDefaultConfiguration>> greenValue = ThreadLocal.withInitial(() -> null);

    public static void setValue(String configurationKey, GreenDefaultConfiguration config) {
        if(Objects.isNull(greenValue.get())) {
            Map<String, GreenDefaultConfiguration> map = new HashMap<>();
            map.put(configurationKey, config);
            greenValue.set(map);
        } else {
            greenValue.get().put(configurationKey, config);
        }
    }

    public static GreenDefaultConfiguration getValue(String configurationKey) {
        if(Objects.nonNull(greenValue.get())) {
            return greenValue.get().get(configurationKey);
        }
        return null;
    }

    public static ThreadLocal<Map<String, GreenDefaultConfiguration>> getGreenValue() {
        return greenValue;
    }

    public static void cleanThreadLocalValue() {
        greenValue.remove();
    }
}
