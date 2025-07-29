package net.sf.esfinge.greenframework.spring.starter.green.custommock.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomMockRequest {

    @NotBlank
    private String key;

    @NotBlank
    private String returnType;

    @NotBlank
    private String customClass;

    @NotBlank
    private String defaultValue;
}
