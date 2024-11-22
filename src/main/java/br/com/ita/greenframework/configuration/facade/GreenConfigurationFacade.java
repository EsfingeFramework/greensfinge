package br.com.ita.greenframework.configuration.facade;

import br.com.ita.greenframework.configuration.GreenThreadLocal;
import br.com.ita.greenframework.dto.annotation.GreenDefaultConfiguration;
import br.com.ita.greenframework.dto.project.GreenConfiguration;
import br.com.ita.greenframework.service.GreenConfigurationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreenConfigurationFacade {

    private final Map<String, GreenDefaultConfiguration> cache = new HashMap<>();
    private final GreenConfigurationService configurationService = new GreenConfigurationService();

    public List<GreenConfiguration> getConfigurations() {
        return configurationService.getConfigurationsInProject();
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
