package net.sf.esfinge.greenframework.configuration.mockprocessor;

import net.sf.esfinge.greenframework.annotation.EnergySavingCustomCalculation;
import net.sf.esfinge.greenframework.configuration.GreenThreadLocal;
import net.sf.esfinge.greenframework.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.dto.annotation.GreenAdjustableNumberConfiguration;
import net.sf.esfinge.greenframework.service.GreenMetricService;
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
