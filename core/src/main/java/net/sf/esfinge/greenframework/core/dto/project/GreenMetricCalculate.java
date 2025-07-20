package net.sf.esfinge.greenframework.core.dto.project;

import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerField;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Map;

@Getter
@Builder
public class GreenMetricCalculate {

    private Method method;
    private ContainerField containerField;
    private Map<String, Object> greenConfigurations;
}
