package net.sf.esfinge.greenframework.core.configuration.energyestimation;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

    protected abstract Double calculateSavedValue(Method method);
}
