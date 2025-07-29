package net.sf.esfinge.greenframework.core.configuration.energyestimation;

import lombok.SneakyThrows;
import net.sf.esfinge.greenframework.core.annotation.EnergySavingCustomCalculation;
import net.sf.esfinge.greenframework.core.configuration.metriccalculate.EnergySavingsCalculator;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenAdjustableNumberConfiguration;
import net.sf.esfinge.greenframework.core.entity.GreenMetricCalculate;
import net.sf.esfinge.greenframework.core.service.GreenConfigurationService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EnergySavingCustomCalculationProcessor extends EnergyEstimationProcessor<EnergySavingCustomCalculation> {

    private final GreenConfigurationService configurationService = new GreenConfigurationService();

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

    protected Map<String, Object> createMapGreenConfigurations(EnergySavingCustomCalculation annotation) {
        Map<String, Object> map = new HashMap<>();
        for (String config : annotation.affectedByConfigurations()) {
            GreenAdjustableNumberConfiguration configuration = configurationService.getConfigurationByType(config, GreenAdjustableNumberConfiguration.class);
            if (Objects.nonNull(configuration)) {
                map.put(configuration.getConfigurationKey(), configuration.getValue());
            }
        }
        return map;
    }


}
