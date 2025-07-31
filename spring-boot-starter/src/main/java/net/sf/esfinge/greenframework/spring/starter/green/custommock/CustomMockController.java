package net.sf.esfinge.greenframework.spring.starter.green.custommock;

import jakarta.validation.Valid;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenCustomMockFacade;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;
import net.sf.esfinge.greenframework.core.dto.project.CustomMockResponse;
import net.sf.esfinge.greenframework.spring.starter.green.custommock.dto.CustomMockRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/green/custom-mocks")
public class CustomMockController {

    @Autowired
    private GreenCustomMockFacade facade;

    @PostMapping
    public void createCustomMock(@RequestBody @Valid CustomMockRequest customMockRequest) {
        facade.createCustomMock(GreenCustomMockConfiguration.builder()
                .key(customMockRequest.getKey())
                .customClass(customMockRequest.getCustomClass())
                .defaultValue(customMockRequest.getDefaultValue())
                .returnType(customMockRequest.getReturnType())
                .build()
        );
    }

    @GetMapping
    public List<CustomMockResponse> getAllConfigurations() {
        return facade.getAllConfigurations();
    }
}
