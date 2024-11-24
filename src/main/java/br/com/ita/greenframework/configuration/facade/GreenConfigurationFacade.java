package br.com.ita.greenframework.configuration.facade;

import br.com.ita.greenframework.configuration.GreenThreadLocal;
import br.com.ita.greenframework.dto.annotation.GreenDefaultConfiguration;
import br.com.ita.greenframework.dto.project.GreenConfiguration;
import br.com.ita.greenframework.service.GreenConfigurationService;
import lombok.Getter;

import java.util.List;

@Getter
public class GreenConfigurationFacade {

    private final GreenConfigurationService configurationService = new GreenConfigurationService();

    public List<GreenConfiguration> getConfigurations() {
        return configurationService.getConfigurationsInProject();
    }

    public void setGeneralConfiguration(GreenDefaultConfiguration config) {
        GreenThreadLocal.setValue(config.getConfigurationKey(), config);
    }

    public void setPersonalConfiguration(GreenDefaultConfiguration config) {
        throw new IllegalArgumentException();
    }

}
