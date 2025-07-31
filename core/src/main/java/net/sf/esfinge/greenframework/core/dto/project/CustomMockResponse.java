package net.sf.esfinge.greenframework.core.dto.project;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomMockResponse {

    private String key;
    private String returnType;
    private String customClass;
    private String defaultValue;
}
