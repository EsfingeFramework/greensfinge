package br.com.ita.greenframework.configurations;

import br.com.ita.greenframework.dto.GreenDefaultConfiguration;
import br.com.ita.greenframework.dto.GreenConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreenConfigurationFacade {

    private final Map<String, GreenDefaultConfiguration> cache = new HashMap<>();

    public List<GreenConfiguration> getConfigurations() {
        br.com.ita.greenframework.configurations.GreenConfiguration greenConfiguration = new br.com.ita.greenframework.configurations.GreenConfiguration();
        return greenConfiguration.getConfigurationsInProject();
    }

    public void setGeneralConfiguration(GreenDefaultConfiguration config) {
        cache.put(config.getConfigurationKey(), config);
        GreenThreadLocal greenThreadLocal = new GreenThreadLocal();
        greenThreadLocal.setValue(cache);
    }

    public void setPersonalConfiguration(GreenDefaultConfiguration config) {

    }

    public Map<String, GreenDefaultConfiguration> getCache() {
        return cache;
    }
}
