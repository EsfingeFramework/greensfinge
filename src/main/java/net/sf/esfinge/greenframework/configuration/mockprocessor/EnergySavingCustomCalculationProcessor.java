package net.sf.esfinge.greenframework.configuration.mockprocessor;

import net.sf.esfinge.greenframework.annotation.EnergySavingCustomCalculation;
import net.sf.esfinge.greenframework.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.configuration.metriccalculate.EnergySavingsCalculator;
import net.sf.esfinge.greenframework.dto.project.GreenMetricCalculate;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

public class EnergySavingCustomCalculationProcessor extends MockProcessor<EnergySavingCustomCalculation> {

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
