package net.sf.esfinge.greenframework.configuration.facade;

import net.sf.esfinge.greenframework.configuration.GreenThreadLocal;
import net.sf.esfinge.greenframework.dto.annotation.GreenDefaultConfiguration;
import net.sf.esfinge.greenframework.dto.project.GreenConfiguration;
import net.sf.esfinge.greenframework.service.GreenConfigurationService;
import lombok.Getter;

import java.util.List;

@Getter
public class GreenConfigurationFacade {

    private final GreenConfigurationService configurationService = new GreenConfigurationService();

    public List<GreenConfiguration> getConfigurationsInProject() {
        return configurationService.getConfigurationsInProject();
    }

    public void setGeneralConfiguration(GreenDefaultConfiguration config) {
        GreenThreadLocal.setValue(config.getConfigurationKey(), config);
    }

    public void setPersonalConfiguration(GreenDefaultConfiguration config) {
        throw new IllegalArgumentException();
    }

}
