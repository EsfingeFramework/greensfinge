package net.sf.esfinge.greenframework.core.configuration.facade;

import lombok.Getter;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenDefaultConfiguration;
import net.sf.esfinge.greenframework.core.service.GreenConfigurationService;

import java.util.List;

@Getter
public class GreenConfigurationFacade {

    private final GreenConfigurationService configurationService = new GreenConfigurationService();

    public void setGeneralConfiguration(GreenDefaultConfiguration config) {
        configurationService.setGeneralConfiguration(config.getConfigurationKey(), config);
    }

    public void setPersonalConfiguration(GreenDefaultConfiguration config) {
        configurationService.setPersonalConfiguration(config.getConfigurationKey(), config);
    }

    public List<GreenDefaultConfiguration> getAllConfigurations() {
        return configurationService.getAllConfigurations();
    }
}
