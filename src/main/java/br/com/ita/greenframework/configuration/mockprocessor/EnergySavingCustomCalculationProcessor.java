package br.com.ita.greenframework.configuration.mockprocessor;

import br.com.ita.greenframework.annotation.EnergySavingCustomCalculation;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.configuration.metriccalculate.EnergySavingsCalculator;
import br.com.ita.greenframework.dto.project.GreenMetricCalculate;
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
