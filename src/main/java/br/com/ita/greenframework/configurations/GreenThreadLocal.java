package br.com.ita.greenframework.configurations;

import br.com.ita.greenframework.dto.Configuration;

import java.util.Map;

public class GreenThreadLocal {

    private static final ThreadLocal<Map<String, Configuration>> greenValue = ThreadLocal.withInitial(() -> null);

    public void setValue(Map<String, Configuration> cache) {
        greenValue.set(cache);
    }

    public Configuration getValue(String configurationKey) {
        return greenValue.get().get(configurationKey);
    }
}
