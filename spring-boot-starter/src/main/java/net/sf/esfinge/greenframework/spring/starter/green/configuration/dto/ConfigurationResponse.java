package net.sf.esfinge.greenframework.spring.starter.green.configuration.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ConfigurationResponse {

    private String configurationKey;
    private String keyContext;
    private String configType;
    private String configScope;
    private Object defaultValue;
}
