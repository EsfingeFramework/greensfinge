package net.sf.esfinge.greenframework.spring.starter.green.configuration;

import jakarta.validation.Valid;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenAdjustableNumberConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.spring.starter.green.configuration.dto.ConfigurationIntDTO;
import net.sf.esfinge.greenframework.spring.starter.green.configuration.dto.ConfigurationStrDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {

    private final GreenConfigurationFacade facade = new GreenConfigurationFacade();

    @PostMapping("/str")
    public void createStrConfiguration(@RequestBody @Valid ConfigurationStrDTO configurationDTO) {
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(configurationDTO.getIgnore())
                .configurationKey(configurationDTO.getKey())
                .strDefaultValue(configurationDTO.getDefaultValue())
                .build()
        );
    }

    @PostMapping("/int")
    public void createIntConfiguration(@RequestBody @Valid ConfigurationIntDTO configurationDTO) {
        facade.setGeneralConfiguration(GreenAdjustableNumberConfiguration.builder()
                .configurationKey(configurationDTO.getKey())
                .value(configurationDTO.getDefaultValue())
                .build()
        );
    }
}
