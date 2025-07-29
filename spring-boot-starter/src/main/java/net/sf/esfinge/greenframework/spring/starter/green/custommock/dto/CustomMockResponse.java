package net.sf.esfinge.greenframework.spring.starter.green.custommock.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomMockResponse {

    private String key;
    private String returnType;
    private String customClass;
    private String defaultValue;
}
