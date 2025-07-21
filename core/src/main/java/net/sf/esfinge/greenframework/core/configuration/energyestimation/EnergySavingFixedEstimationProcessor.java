package net.sf.esfinge.greenframework.core.configuration.energyestimation;

import net.sf.esfinge.greenframework.core.annotation.EnergySavingFixedEstimation;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerField;

import java.lang.reflect.Method;
import java.util.Objects;

public class EnergySavingFixedEstimationProcessor extends EnergyEstimationProcessor<EnergySavingFixedEstimation> {

    @Override
    protected Double calculateSavedValue(Method method, ContainerField containerField) {
        EnergySavingFixedEstimation annotation = method.getAnnotation(EnergySavingFixedEstimation.class);

        if(Objects.nonNull(annotation)) {
            return annotation.energySavedValue();
        }

        return null;
    }
}
