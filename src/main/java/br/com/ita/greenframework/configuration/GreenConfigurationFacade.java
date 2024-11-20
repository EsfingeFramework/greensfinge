package br.com.ita.greenframework.configuration;

import br.com.ita.greenframework.dto.GreenConfiguration;
import br.com.ita.greenframework.dto.GreenDefaultConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreenConfigurationFacade {

    private final Map<String, GreenDefaultConfiguration> cache = new HashMap<>();
    private final GreenConfigConfiguration greenConfigConfiguration = new GreenConfigConfiguration();

    public List<GreenConfiguration> getConfigurations() {
        return greenConfigConfiguration.getConfigurationsInProject();
    }

    public void setGeneralConfiguration(GreenDefaultConfiguration config) {
        cache.put(config.getConfigurationKey(), config);
        GreenThreadLocal greenThreadLocal = new GreenThreadLocal();
        greenThreadLocal.setValue(cache);
    }

    public void setPersonalConfiguration(GreenDefaultConfiguration config) {
        throw new IllegalArgumentException();
    }

    public Map<String, GreenDefaultConfiguration> getCache() {
        return cache;
    }
}
