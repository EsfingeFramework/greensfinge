package net.sf.esfinge.greenframework.core.dto.project;

import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Map;

@Getter
@Builder
public class GreenMetricCalculate {

    private Method method;
    private Map<String, Object> greenConfigurations;
}
