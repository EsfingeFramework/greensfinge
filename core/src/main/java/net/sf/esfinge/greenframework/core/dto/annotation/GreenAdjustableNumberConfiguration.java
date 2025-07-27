package net.sf.esfinge.greenframework.core.dto.annotation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GreenAdjustableNumberConfiguration extends GreenDefaultConfiguration {

    private Number value;

    @Override
    public Map<String, Object> toMap() {
        return new ObjectMapper().convertValue(this, new TypeReference<>() {});
    }

    @Override
    public GreenDefaultConfiguration toObject(Map<String, Object> map) {
        return new ObjectMapper().convertValue(map, GreenAdjustableNumberConfiguration.class);
    }
}
