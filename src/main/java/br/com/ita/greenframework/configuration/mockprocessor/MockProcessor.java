package br.com.ita.greenframework.configuration.mockprocessor;

import br.com.ita.greenframework.annotation.EnergySavingCustomCalculation;
import br.com.ita.greenframework.configuration.GreenThreadLocal;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.dto.annotation.GreenAdjustableNumberConfiguration;
import br.com.ita.greenframework.service.GreenMetricService;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public abstract class MockProcessor<T extends Annotation> {

    protected final GreenMetricService greenMetricService = new GreenMetricService();

    public void process(Method method, ContainerField containerField) {
        logHasAnnotation(method);

        Double savedValue = calculateSavedValue(method, containerField);
        String key = String.format("%s#%s", method.getDeclaringClass().getName(), method.getName());

        greenMetricService.save(savedValue, key, containerField);
    }

    @SuppressWarnings("unchecked")
    private void logHasAnnotation(Method method) {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
            if(!method.isAnnotationPresent((Class<? extends Annotation>) type)) {
                log.debug("The {}#{} method is mocked, but does not contain the @{} annotation",
                        method.getDeclaringClass().getName(), method.getName(), type.getTypeName());
            }
        }
    }

    protected Map<String, Object> createMapGreenConfigurations(EnergySavingCustomCalculation annotation) {
        Map<String, Object> map = new HashMap<>();
        for (String config : annotation.affectedByConfigurations()) {
            GreenAdjustableNumberConfiguration value = GreenThreadLocal.getValue(config);
            if (Objects.nonNull(value)) {
                map.put(value.getConfigurationKey(), value.getValue());
            }
        }
        return map;
    }

    protected abstract Double calculateSavedValue(Method method, ContainerField containerField);
}
