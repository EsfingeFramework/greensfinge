package net.sf.esfinge.greenframework.spring.starter.green.scanmapping;

import net.sf.esfinge.greenframework.core.configuration.facade.GreenScanMappingFacade;
import net.sf.esfinge.greenframework.core.dto.project.response.GreenScanMappingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/green/scan-mapping")
public class ScanMappingController {

    @Autowired
    private GreenScanMappingFacade facade;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void scanMappingConfigurations() {
        facade.scanMappingConfigurations();
    }

    @GetMapping
    public List<GreenScanMappingResponse> getAllConfigurations() {
        return facade.getConfigurationsInProject();
    }
}
