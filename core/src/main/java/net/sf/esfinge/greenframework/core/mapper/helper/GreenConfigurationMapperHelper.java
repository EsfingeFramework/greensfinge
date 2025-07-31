package net.sf.esfinge.greenframework.core.mapper.helper;

import net.sf.esfinge.greenframework.core.dto.annotation.GreenAdjustableNumberConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenDefaultConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.core.dto.project.ConfigurationResponse;
import net.sf.esfinge.greenframework.core.entity.enuns.GreenConfigurationScope;
import net.sf.esfinge.greenframework.core.entity.enuns.GreenConfigurationType;
import org.mapstruct.Named;

import java.util.Optional;

public class GreenConfigurationMapperHelper {

    @Named("customConfigurationMapper")
    public static ConfigurationResponse mapCustom(GreenDefaultConfiguration config) {
        if (config == null) return null;

        ConfigurationResponse response = new ConfigurationResponse();

        response.setConfigurationKey(config.getConfigurationKey());
        response.setKeyContext(config.getKeyContext());
        response.setConfigScope(Optional.ofNullable(config.getKeyContext())
                .map(e -> GreenConfigurationScope.PERSONAL)
                .orElse(GreenConfigurationScope.GENERAL)
        );

        if(config instanceof GreenSwitchConfiguration greenConfig) {
            response.setIgnore(greenConfig.isIgnore());
            response.setConfigType(GreenConfigurationType.valueOf(GreenSwitchConfiguration.class.getSimpleName()));
            response.setDefaultValue(greenConfig.getDefaultValue());
        }

        if(config instanceof GreenAdjustableNumberConfiguration greenConfig) {
            response.setConfigType(GreenConfigurationType.valueOf(GreenAdjustableNumberConfiguration.class.getSimpleName()));
            response.setDefaultValue(greenConfig.getValue());
        }

        return response;
    }
}
