package net.sf.esfinge.greenframework.core.configuration.facade;

import lombok.Getter;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenDefaultConfiguration;
import net.sf.esfinge.greenframework.core.dto.project.ConfigurationResponse;
import net.sf.esfinge.greenframework.core.mapper.GreenConfigurationMapper;
import net.sf.esfinge.greenframework.core.service.GreenConfigurationService;

import java.util.List;

@Getter
public class GreenConfigurationFacade {

    private final GreenConfigurationService configurationService = new GreenConfigurationService();

    public void clearAllConfigurations() {
        configurationService.clearAllConfigurations();
    }

    public void setGeneralConfiguration(GreenDefaultConfiguration config) {
        configurationService.setGeneralConfiguration(config.getConfigurationKey(), config);
    }

    public void setPersonalConfiguration(GreenDefaultConfiguration config) {
        configurationService.setPersonalConfiguration(config.getConfigurationKey(), config);
    }

    public List<ConfigurationResponse> getAllConfigurations() {
        return GreenConfigurationMapper.INSTANCE.toResponse(configurationService.getAllConfigurations());
    }
}
