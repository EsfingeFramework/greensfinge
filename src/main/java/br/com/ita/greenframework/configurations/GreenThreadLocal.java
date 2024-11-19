package br.com.ita.greenframework.configurations;

import br.com.ita.greenframework.dto.GreenDefaultConfiguration;

import java.util.Map;

public class GreenThreadLocal {

    private static final ThreadLocal<Map<String, GreenDefaultConfiguration>> greenValue = ThreadLocal.withInitial(() -> null);

    public void setValue(Map<String, GreenDefaultConfiguration> cache) {
        greenValue.set(cache);
    }

    public GreenDefaultConfiguration getValue(String configurationKey) {
        return greenValue.get().get(configurationKey);
    }
}
