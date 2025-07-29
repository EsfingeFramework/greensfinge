package net.sf.esfinge.greenframework.spring.starter.green.configuration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalConfigurationIntRequest {

    @NotBlank
    private String key;
    @NotNull
    private Number defaultValue;
    @NotBlank
    private String keyContext;
}
