package net.sf.esfinge.greenframework.configuration.mockprocessor;

import net.sf.esfinge.greenframework.annotation.EnergySavingFixedEstimation;
import net.sf.esfinge.greenframework.configuration.esfinge.dto.ContainerField;

import java.lang.reflect.Method;
import java.util.Objects;

public class EnergySavingFixedEstimationProcessor extends MockProcessor<EnergySavingFixedEstimation> {

    @Override
    protected Double calculateSavedValue(Method method, ContainerField containerField) {
        EnergySavingFixedEstimation annotation = method.getAnnotation(EnergySavingFixedEstimation.class);

        if(Objects.nonNull(annotation)) {
            return annotation.energySavedValue();
        }

        return null;
    }
}
