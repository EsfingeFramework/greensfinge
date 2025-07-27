package net.sf.esfinge.greenframework.core.configuration.facade;

import lombok.Getter;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;
import net.sf.esfinge.greenframework.core.service.GreenCustomMockService;

import java.util.List;

@Getter
public class GreenCustomMockFacade {

    private final GreenCustomMockService service = new GreenCustomMockService();

    public void createCustomMock(GreenCustomMockConfiguration mockConfiguration) {
        service.createCustomMock(mockConfiguration);
    }

    public List<GreenCustomMockConfiguration> getAllConfigurations() {
        return service.getAllConfigurations();
    }
}
