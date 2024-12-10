package br.com.ita.greenframework.configuration.mockprocessor;

import br.com.ita.greenframework.annotation.EnergySavingCustomCalculation;
import br.com.ita.greenframework.annotation.EnergySavingFixedEstimation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class GreenMockProcessor {

    private static GreenMockProcessor instance;
    private static final Map<Class<? extends Annotation>, MockProcessor> processorTypes = new HashMap<>();

    public GreenMockProcessor () {
        populateData();
    }

    public static GreenMockProcessor getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (GreenMockProcessor.class) {
                if (Objects.isNull(instance)) {
                    instance = new GreenMockProcessor();
                }
            }
        }
        return instance;
    }

    protected void populateData() {
        processorTypes.put(EnergySavingCustomCalculation.class, new EnergySavingCustomCalculationProcessor());
        processorTypes.put(EnergySavingFixedEstimation.class, new EnergySavingFixedEstimationProcessor());
    }

    public MockProcessor getProcessor(Class<?> clazz) {
        return Optional.of(processorTypes)
                .filter(typeProcessor -> typeProcessor.containsKey(clazz))
                .map(e -> e.get(clazz))
                .orElse(null);
    }

}
