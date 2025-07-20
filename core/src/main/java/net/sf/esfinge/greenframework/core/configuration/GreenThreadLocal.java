package net.sf.esfinge.greenframework.core.configuration;

import net.sf.esfinge.greenframework.core.dto.annotation.GreenDefaultConfiguration;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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

    @SuppressWarnings("unchecked")
    public static <T> T getValue(String configurationKey) {
        return Optional.ofNullable(configurationKey)
                .map(e->  {
                    if(Objects.nonNull(greenValue.get())) {
                        return (T) greenValue.get().get(configurationKey);
                    } else {
                        return null;
                    }
                })
                .orElse(null);
    }

    public static void cleanThreadLocalValue() {
        greenValue.remove();
    }
}
