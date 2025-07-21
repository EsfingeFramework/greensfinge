package net.sf.esfinge.greenframework.core.configuration.energyestimation;

import net.sf.esfinge.greenframework.core.annotation.EnergySavingCustomCalculation;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.core.configuration.metriccalculate.EnergySavingsCalculator;
import net.sf.esfinge.greenframework.core.dto.project.GreenMetricCalculate;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

public class EnergySavingCustomCalculationProcessor extends EnergyEstimationProcessor<EnergySavingCustomCalculation> {

    @Override
    @SneakyThrows
    protected Double calculateSavedValue(Method method, ContainerField containerField) {
        EnergySavingCustomCalculation annotation = method.getAnnotation(EnergySavingCustomCalculation.class);

        EnergySavingsCalculator instance = annotation.implementation().getDeclaredConstructor().newInstance();
        return instance.calculateSavedValue(GreenMetricCalculate.builder()
                .method(method)
                .containerField(containerField)
                .greenConfigurations(createMapGreenConfigurations(annotation))
                .build());
    }


}
