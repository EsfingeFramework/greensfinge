package br.com.ita.greenframework.configurations;

import br.com.ita.greenframework.dto.Configuration;
import br.com.ita.greenframework.dto.GreenConfigurationDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreenConfigurationFacade {

    private final Map<String, Configuration> CACHE = new HashMap<>();

    public List<GreenConfigurationDTO> getConfigurations() {
        GreenConfiguration greenConfiguration = new GreenConfiguration();
        return greenConfiguration.getConfigurationsInProject();
    }

    public void setGeneralConfiguration(Configuration config) {
        CACHE.put(config.getConfigurationKey(), config);
        GreenThreadLocal greenThreadLocal = new GreenThreadLocal();
        greenThreadLocal.setValue(CACHE);
    }

    public void setPersonalConfiguration(Configuration config) {

    }

    public Map<String, Configuration> getCache() {
        return CACHE;
    }
}
