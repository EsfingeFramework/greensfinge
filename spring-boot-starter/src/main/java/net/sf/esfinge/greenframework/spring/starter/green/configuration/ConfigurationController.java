package net.sf.esfinge.greenframework.spring.starter.green.configuration;

import jakarta.validation.Valid;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenAdjustableNumberConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.core.dto.project.ConfigurationResponse;
import net.sf.esfinge.greenframework.spring.starter.green.configuration.dto.GeneralConfigurationIntRequest;
import net.sf.esfinge.greenframework.spring.starter.green.configuration.dto.GeneralConfigurationStrRequest;
import net.sf.esfinge.greenframework.spring.starter.green.configuration.dto.PersonalConfigurationIntRequest;
import net.sf.esfinge.greenframework.spring.starter.green.configuration.dto.PersonalConfigurationStrRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/green/configurations")
public class ConfigurationController {

    @Autowired
    private GreenConfigurationFacade facade;

    @PostMapping("/general/str")
    public void createGeneralStrConfiguration(@RequestBody @Valid GeneralConfigurationStrRequest configurationRequest) {
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(configurationRequest.getIgnore())
                .configurationKey(configurationRequest.getKey())
                .defaultValue(configurationRequest.getDefaultValue())
                .build()
        );
    }

    @PostMapping("/general/int")
    public void createGeneralIntConfiguration(@RequestBody @Valid GeneralConfigurationIntRequest configurationRequest) {
        facade.setGeneralConfiguration(GreenAdjustableNumberConfiguration.builder()
                .configurationKey(configurationRequest.getKey())
                .value(configurationRequest.getDefaultValue())
                .build()
        );
    }

    @PostMapping("/personal/str")
    public void createPersonalStrConfiguration(@RequestBody @Valid PersonalConfigurationStrRequest configurationRequest) {
        facade.setPersonalConfiguration(GreenSwitchConfiguration.builder()
                .ignore(configurationRequest.getIgnore())
                .configurationKey(configurationRequest.getKey())
                .defaultValue(configurationRequest.getDefaultValue())
                .keyContext(configurationRequest.getKeyContext())
                .build()
        );
    }

    @PostMapping("/personal/int")
    public void createPersonalIntConfiguration(@RequestBody @Valid PersonalConfigurationIntRequest configurationRequest) {
        facade.setPersonalConfiguration(GreenAdjustableNumberConfiguration.builder()
                .configurationKey(configurationRequest.getKey())
                .value(configurationRequest.getDefaultValue())
                .keyContext(configurationRequest.getKeyContext())
                .build()
        );
    }

    @GetMapping
    public List<ConfigurationResponse> getAllConfigurations() {
        return facade.getAllConfigurations();
    }
}
