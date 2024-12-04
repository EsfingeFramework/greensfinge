package br.com.ita.greenframework.configuration.mockprocessor;

import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.service.GreenMetricService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MetricMockProcessor extends MockProcessor {

    private final GreenMetricService greenMetricService = new GreenMetricService();

    @Override
    public void process(Field field, Method method, ContainerField containerField) {
        greenMetricService.save(field, method, containerField);
    }
}
