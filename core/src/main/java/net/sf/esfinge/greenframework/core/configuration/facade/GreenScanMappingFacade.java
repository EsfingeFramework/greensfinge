package net.sf.esfinge.greenframework.core.configuration.facade;

import lombok.Getter;
import net.sf.esfinge.greenframework.core.dto.project.response.GreenScanMappingResponse;
import net.sf.esfinge.greenframework.core.mapper.GreenScanMappingMapper;
import net.sf.esfinge.greenframework.core.service.GreenScanMappingService;

import java.util.List;

@Getter
public class GreenScanMappingFacade {

    private final GreenScanMappingService greenScanMappingService = new GreenScanMappingService();

    public List<GreenScanMappingResponse> getConfigurationsInProject() {
        return GreenScanMappingMapper.INSTANCE.toResponse(greenScanMappingService.getConfigurationsInProject());
    }

    public void scanMappingConfigurations() {
        greenScanMappingService.scanForConfigurations();
    }
}
