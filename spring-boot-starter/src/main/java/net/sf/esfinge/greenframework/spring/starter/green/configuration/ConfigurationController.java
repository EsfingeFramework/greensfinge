package net.sf.esfinge.greenframework.spring.starter.green.configuration;

import jakarta.validation.Valid;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenAdjustableNumberConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenDefaultConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.spring.starter.green.configuration.dto.GeneralConfigurationIntDTO;
import net.sf.esfinge.greenframework.spring.starter.green.configuration.dto.GeneralConfigurationStrDTO;
import net.sf.esfinge.greenframework.spring.starter.green.configuration.dto.PersonalConfigurationIntDTO;
import net.sf.esfinge.greenframework.spring.starter.green.configuration.dto.PersonalConfigurationStrDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/configurations")
public class ConfigurationController {

    private final GreenConfigurationFacade facade = new GreenConfigurationFacade();

    @PostMapping("/general/str")
    public void createGeneralStrConfiguration(@RequestBody @Valid GeneralConfigurationStrDTO configurationDTO) {
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(configurationDTO.getIgnore())
                .configurationKey(configurationDTO.getKey())
                .strDefaultValue(configurationDTO.getDefaultValue())
                .build()
        );
    }

    @PostMapping("/general/int")
    public void createGeneralIntConfiguration(@RequestBody @Valid GeneralConfigurationIntDTO configurationDTO) {
        facade.setGeneralConfiguration(GreenAdjustableNumberConfiguration.builder()
                .configurationKey(configurationDTO.getKey())
                .value(configurationDTO.getDefaultValue())
                .build()
        );
    }

    @PostMapping("/personal/str")
    public void createPersonalStrConfiguration(@RequestBody @Valid PersonalConfigurationStrDTO configurationDTO) {
        facade.setPersonalConfiguration(GreenSwitchConfiguration.builder()
                .ignore(configurationDTO.getIgnore())
                .configurationKey(configurationDTO.getKey())
                .strDefaultValue(configurationDTO.getDefaultValue())
                .keyContext(configurationDTO.getKeyContext())
                .build()
        );
    }

    @PostMapping("/personal/int")
    public void createPersonalIntConfiguration(@RequestBody @Valid PersonalConfigurationIntDTO configurationDTO) {
        facade.setPersonalConfiguration(GreenAdjustableNumberConfiguration.builder()
                .configurationKey(configurationDTO.getKey())
                .value(configurationDTO.getDefaultValue())
                .keyContext(configurationDTO.getKeyContext())
                .build()
        );
    }

    @GetMapping
    public List<GreenDefaultConfiguration> getAllConfigurations() {
        return facade.getAllConfigurations();
    }
}
