package net.sf.esfinge.greenframework.core.dto.annotation;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.sf.esfinge.greenframework.core.util.GreenConstant;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GreenSwitchConfiguration extends GreenDefaultConfiguration {

    private boolean ignore;

    @Builder.Default
    private String strDefaultValue = GreenConstant.STR_DEFAULT_VALUE;
    @Builder.Default
    private Double numberDefaultValue = GreenConstant.DOUBLE_DEFAULT_VALUE;

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("type", "GreenSwitchConfiguration");
        map.put("configurationKey", this.getConfigurationKey());
        map.put("ignore", this.ignore);
        map.put("strDefaultValue", this.strDefaultValue);
        map.put("numberDefaultValue", this.numberDefaultValue);

        return map;
    }

    @Override
    public void toObject(Map<String, Object> map) {
        this.ignore = Boolean.parseBoolean(map.get("ignore").toString());
        this.setConfigurationKey(map.get("configurationKey").toString());
        this.strDefaultValue = map.get("strDefaultValue").toString();
        this.numberDefaultValue = Double.valueOf(map.get("numberDefaultValue").toString());
    }

}
