package net.sf.esfinge.greenframework.core.configuration.energyestimation;

import net.sf.esfinge.greenframework.core.annotation.EnergySavingCustomCalculation;
import net.sf.esfinge.greenframework.core.annotation.EnergySavingFixedEstimation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class GreenEnergyMetricsProcessor {

    private static GreenEnergyMetricsProcessor instance;
    private static final Map<Class<? extends Annotation>, EnergyEstimationProcessor<?>> processorTypes = new HashMap<>();

    public GreenEnergyMetricsProcessor() {
        populateData();
    }

    public static GreenEnergyMetricsProcessor getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (GreenEnergyMetricsProcessor.class) {
                if (Objects.isNull(instance)) {
                    instance = new GreenEnergyMetricsProcessor();
                }
            }
        }
        return instance;
    }

    protected void populateData() {
        processorTypes.put(EnergySavingCustomCalculation.class, new EnergySavingCustomCalculationProcessor());
        processorTypes.put(EnergySavingFixedEstimation.class, new EnergySavingFixedEstimationProcessor());
    }

    public EnergyEstimationProcessor<?> getMetricProcessor(Class<?> clazz) {
        return Optional.of(processorTypes)
                .filter(typeProcessor -> typeProcessor.containsKey(clazz))
                .map(e -> e.get(clazz))
                .orElse(null);
    }

}
