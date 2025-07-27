package net.sf.esfinge.greenframework.spring.starter.green.custommock;

import jakarta.validation.Valid;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenCustomMockFacade;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;
import net.sf.esfinge.greenframework.spring.starter.green.custommock.dto.CustomMockDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/green/custom-mocks")
public class CustomMockController {

    @Autowired
    private GreenCustomMockFacade facade;

    @PostMapping
    public void createCustomMock(@RequestBody @Valid CustomMockDTO customMockDTO) {
        facade.createCustomMock(GreenCustomMockConfiguration.builder()
                .key(customMockDTO.getKey())
                .customClass(customMockDTO.getCustomClass())
                .defaultValue(customMockDTO.getDefaultValue())
                .returnType(customMockDTO.getReturnType())
                .build()
        );
    }

    @GetMapping
    public List<GreenCustomMockConfiguration> getAllConfigurations() {
        return facade.getAllConfigurations();
    }
}
