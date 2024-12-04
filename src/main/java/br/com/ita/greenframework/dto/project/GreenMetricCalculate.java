package br.com.ita.greenframework.dto.project;

import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

@Getter
@Builder
public class GreenMetricCalculate {

    private Method method;
    private Field field;
    private ContainerField containerField;
    private Map<String, Object> greenConfigurations;
}
