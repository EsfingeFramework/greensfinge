package net.sf.esfinge.greenframework.core.configuration.energyestimation;

import net.sf.esfinge.greenframework.core.annotation.EnergySavingFixedEstimation;

import java.lang.reflect.Method;
import java.util.Objects;

public class EnergySavingFixedEstimationProcessor extends EnergyEstimationProcessor<EnergySavingFixedEstimation> {

    @Override
    protected Double calculateSavedValue(Method method) {
        EnergySavingFixedEstimation annotation = method.getAnnotation(EnergySavingFixedEstimation.class);

        if(Objects.nonNull(annotation)) {
            return annotation.energySavedValue();
        }

        return null;
    }
}
