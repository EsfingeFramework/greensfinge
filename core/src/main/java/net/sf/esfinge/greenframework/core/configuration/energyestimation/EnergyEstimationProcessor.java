package net.sf.esfinge.greenframework.core.configuration.energyestimation;

import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.greenframework.core.annotation.EnergySavingCustomCalculation;
import net.sf.esfinge.greenframework.core.configuration.GreenThreadLocal;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenAdjustableNumberConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public abstract class EnergyEstimationProcessor<T extends Annotation> {

    public Double processEnergyEstimation(Method method) {
        logHasAnnotation(method);

        return calculateSavedValue(method);
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

    protected abstract Double calculateSavedValue(Method method);
}
