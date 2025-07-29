package net.sf.esfinge.greenframework.spring.starter.green.configuration;

import jakarta.validation.Valid;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenAdjustableNumberConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.spring.starter.green.configuration.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
                .strDefaultValue(configurationRequest.getDefaultValue())
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
                .strDefaultValue(configurationRequest.getDefaultValue())
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
        return facade.getAllConfigurations().stream()
                .map(config -> {
                    Map<String, Object> configMap = config.toMap();
                    return ConfigurationResponse.builder()
                            .configType(configMap.get("type") == null ? null : configMap.get("type").toString())
                            .configurationKey(config.getConfigurationKey())
                            .keyContext(config.getKeyContext())
                            .defaultValue(configMap.get("type") == null ? null : configMap.get("type").toString())
                            .build();
                })
                .toList();
    }
}
