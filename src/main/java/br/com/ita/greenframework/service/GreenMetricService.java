package br.com.ita.greenframework.service;

import br.com.ita.greenframework.annotation.EnergySavingCustomCalculation;
import br.com.ita.greenframework.configuration.GreenThreadLocal;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.configuration.metriccalculate.EnergySavingsCalculator;
import br.com.ita.greenframework.configuration.metriccalculate.NoMetricCalculate;
import br.com.ita.greenframework.dao.GreenFactoryDao;
import br.com.ita.greenframework.dao.contract.GreenMetricDao;
import br.com.ita.greenframework.dao.memory.GreenMetricDaoImpl;
import br.com.ita.greenframework.dto.annotation.GreenAdjustableNumberConfiguration;
import br.com.ita.greenframework.dto.project.GreenMetric;
import br.com.ita.greenframework.dto.project.GreenMetricCalculate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class GreenMetricService {

    private final GreenMetricDao greenMetricDao = GreenFactoryDao.getInstance().create(GreenMetricDaoImpl.class);

    public void save(Field field, Method method, ContainerField containerField) {
        EnergySavingCustomCalculation annotation = getGreenMetricValue(field, method);

        if (Objects.isNull(annotation)) {
            log.debug("The {}#{} method is mocked, but does not contain the @GreenMetric annotation",
                    method.getDeclaringClass().getName(), method.getName());
        } else {
            Double metricValue = setMetricSavedValue(field, method, containerField, annotation);
            String key = getKeyConfiguration(field, method);
            GreenMetric metric = greenMetricDao.findById(key);

            if (Objects.isNull(metric)) {
                metric = GreenMetric.builder().
                        method(key)
                        .metricSavedValue(metricValue)
                        .containerField(containerField)
                        .countCalled(1)
                        .measuredTime(LocalDateTime.now())
                        .build();
            } else {
                metric.setCountCalled(metric.getCountCalled() + 1);
            }
            greenMetricDao.save(metric);
        }
    }

    private String getKeyConfiguration(Field field, Method method) {
        if (Objects.nonNull(field)) {
            return String.format("%s#%s", field.getDeclaringClass().getName(), field.getName());
        } else {
            return String.format("%s#%s", method.getDeclaringClass().getName(), method.getName());
        }
    }

    private EnergySavingCustomCalculation getGreenMetricValue(Field field, Method method) {
        if (Objects.nonNull(field)) {
            return field.getAnnotation(EnergySavingCustomCalculation.class);
        } else {
            return method.getAnnotation(EnergySavingCustomCalculation.class);
        }
    }

    @SneakyThrows
    private Double setMetricSavedValue(Field field, Method method, ContainerField containerField, EnergySavingCustomCalculation annotation) {
        if (NoMetricCalculate.class.equals(annotation.implementation())) {
            return annotation.energySavedValue();
        } else {
            EnergySavingsCalculator instance = annotation.implementation().getDeclaredConstructor().newInstance();
            return instance.calculateSavedValue(GreenMetricCalculate.builder()
                            .field(field)
                            .method(method)
                            .containerField(containerField)
                            .greenConfigurations(createMapGreenConfigurations(annotation))
                    .build());
        }
    }

    private Map<String, Object> createMapGreenConfigurations(EnergySavingCustomCalculation annotation) {
        Map<String, Object> map = new HashMap<>();
        for (String config : annotation.affectedByConfigurations()) {
            GreenAdjustableNumberConfiguration value = GreenThreadLocal.getValue(config);
            map.put(value.getConfigurationKey(), value.getValue());
        }
        return map;
    }

    public List<GreenMetric> getSavedEnergy() {
        return greenMetricDao.getSavedEnergy();
    }
}
