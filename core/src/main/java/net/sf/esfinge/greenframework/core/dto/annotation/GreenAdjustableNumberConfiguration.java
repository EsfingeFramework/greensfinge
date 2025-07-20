package net.sf.esfinge.greenframework.core.dto.annotation;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
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
        Map<String, Object> map = new HashMap<>();

        map.put("type", "GreenAdjustableNumberConfiguration");
        map.put("configurationKey", this.getConfigurationKey());
        map.put("value", this.value);

        return map;
    }

    @Override
    public void toObject(Map<String, Object> map) {
        this.value = (Number) map.get("value");
        this.setConfigurationKey(map.get("configurationKey").toString());
    }
}
