package net.sf.esfinge.greenframework.core.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.core.configuration.mockprocessor.GreenEnergyMetricsProcessor;
import net.sf.esfinge.greenframework.core.configuration.mockprocessor.GreenReturnMockValue;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class GreenProxyResolverService {

    private final GreenConfigurationService configurationService = new GreenConfigurationService();
    private final GreenReturnMockValue greenReturnMockValue = new GreenReturnMockValue();

    @SneakyThrows
    public Object resolveMethodInterceptCall(Object originalBean, Method method, Object[] args, ContainerField containerField) {
        GreenSwitchConfiguration configuration = null;
        GreenConfigKey configKey = method.getAnnotation(GreenConfigKey.class);

        if(Objects.isNull(configKey)) {
            log.debug("The {}#{} method his mocked, but does not contain the @GreenConfigKey annotation",
                    method.getDeclaringClass().getName(), method.getName());
        } else {
            configuration = configurationService.getConfigurationByType(configKey.value(), GreenSwitchConfiguration.class);
        }

        if(Objects.isNull(configKey) || Objects.isNull(configuration) || !configuration.isIgnore()) {
            return method.invoke(originalBean , args);
        }

        processEnergyMetric(method, containerField);

        return greenReturnMockValue.getReturnValue(method, configuration);
    }

    private void processEnergyMetric(Method method, ContainerField containerField) {
        for (Annotation annotation : method.getAnnotations()) {
            Optional.ofNullable(GreenEnergyMetricsProcessor.getInstance().getMetricProcessor(annotation.annotationType()))
                    .ifPresent(metricProcessor -> metricProcessor.processEnergyEstimation(method, containerField));
        }
    }
}
