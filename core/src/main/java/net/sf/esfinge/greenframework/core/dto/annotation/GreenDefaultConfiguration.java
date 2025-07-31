package net.sf.esfinge.greenframework.core.dto.annotation;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "configType")
public abstract class GreenDefaultConfiguration {

    private String configurationKey;
    private String keyContext;

    public abstract Map<String, Object> toMap();

    public abstract GreenDefaultConfiguration toObject(Map<String, Object> map);
}
