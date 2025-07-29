package net.sf.esfinge.greenframework.spring.starter.green.configuration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalConfigurationStrRequest {

    @NotBlank
    private String key;
    @NotNull
    private Boolean ignore;
    @NotBlank
    private String defaultValue;
    @NotBlank
    private String keyContext;
}
