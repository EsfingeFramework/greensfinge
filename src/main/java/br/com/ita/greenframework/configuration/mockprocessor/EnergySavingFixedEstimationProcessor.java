package br.com.ita.greenframework.configuration.mockprocessor;

import br.com.ita.greenframework.annotation.EnergySavingFixedEstimation;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;

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
