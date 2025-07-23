package net.sf.esfinge.greenframework.core.configuration.energyestimation;

import lombok.SneakyThrows;
import net.sf.esfinge.greenframework.core.annotation.EnergySavingCustomCalculation;
import net.sf.esfinge.greenframework.core.configuration.metriccalculate.EnergySavingsCalculator;
import net.sf.esfinge.greenframework.core.dto.project.GreenMetricCalculate;

import java.lang.reflect.Method;

public class EnergySavingCustomCalculationProcessor extends EnergyEstimationProcessor<EnergySavingCustomCalculation> {

    @Override
    @SneakyThrows
    protected Double calculateSavedValue(Method method) {
        EnergySavingCustomCalculation annotation = method.getAnnotation(EnergySavingCustomCalculation.class);

        EnergySavingsCalculator instance = annotation.implementation().getDeclaredConstructor().newInstance();
        return instance.calculateSavedValue(GreenMetricCalculate.builder()
                .method(method)
                .greenConfigurations(createMapGreenConfigurations(annotation))
                .build());
    }


}
