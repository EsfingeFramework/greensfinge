package net.sf.esfinge.greenframework.core.configuration.facade;

import lombok.Getter;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;
import net.sf.esfinge.greenframework.core.dto.project.CustomMockResponse;
import net.sf.esfinge.greenframework.core.mapper.GreenCustomMockMapper;
import net.sf.esfinge.greenframework.core.service.GreenCustomMockService;

import java.util.List;

@Getter
public class GreenCustomMockFacade {

    private final GreenCustomMockService service = new GreenCustomMockService();

    public void createCustomMock(GreenCustomMockConfiguration mockConfiguration) {
        service.createCustomMock(mockConfiguration);
    }

    public List<CustomMockResponse> getAllConfigurations() {
        return GreenCustomMockMapper.INSTANCE.toResponse(service.getAllConfigurations());
    }
}
