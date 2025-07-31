package net.sf.esfinge.greenframework.core.dto.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import net.sf.esfinge.greenframework.core.entity.enuns.GreenConfigurationScope;
import net.sf.esfinge.greenframework.core.entity.enuns.GreenConfigurationType;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
public class ConfigurationResponse {

    private Boolean ignore;
    private String configurationKey;
    private String keyContext;
    private GreenConfigurationType configType;
    private GreenConfigurationScope configScope;
    private Object defaultValue;
}
