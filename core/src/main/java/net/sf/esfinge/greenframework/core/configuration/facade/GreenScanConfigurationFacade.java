package net.sf.esfinge.greenframework.core.configuration.facade;

import lombok.Getter;
import net.sf.esfinge.greenframework.core.dto.project.ScanGreenConfiguration;
import net.sf.esfinge.greenframework.core.service.GreenScanConfigurationService;

import java.util.List;

@Getter
public class GreenScanConfigurationFacade {

    private GreenScanConfigurationService scanConfigurationService = new GreenScanConfigurationService();

    public List<ScanGreenConfiguration> getConfigurationsInProject() {
        return scanConfigurationService.getConfigurationsInProject();
    }
}
