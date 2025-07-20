package net.sf.esfinge.greenframework.core.dto.annotation;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class GreenDefaultConfiguration {

    private String configurationKey;

    public abstract Map<String, Object> toMap();

    public abstract void toObject(Map<String, Object> map);
}
