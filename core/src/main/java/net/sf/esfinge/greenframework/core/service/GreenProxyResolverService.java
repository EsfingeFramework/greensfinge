package net.sf.esfinge.greenframework.core.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.core.configuration.energyestimation.GreenEnergyMetricsProcessor;
import net.sf.esfinge.greenframework.core.configuration.energyestimation.GreenReturnMockValue;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class GreenProxyResolverService {

    protected final GreenMetricService greenMetricService = new GreenMetricService();
    private final GreenConfigurationService configurationService = new GreenConfigurationService();
    private final GreenReturnMockValue greenReturnMockValue = new GreenReturnMockValue();

    @SneakyThrows
    public Object resolveMethodInterceptCall(Object originalBean, Method method, Object[] args, ContainerField containerField) {
        GreenSwitchConfiguration greenConfiguration = null;
        GreenConfigKey configKey = method.getAnnotation(GreenConfigKey.class);

        if(Objects.isNull(configKey)) {
            log.debug("The {}#{} method his mocked, but does not contain the @GreenConfigKey annotation",
                    method.getDeclaringClass().getName(), method.getName());
        } else {
            greenConfiguration = configurationService.getConfigurationByType(configKey.value(), GreenSwitchConfiguration.class);
        }

        boolean realCall = Objects.isNull(configKey) || Objects.isNull(greenConfiguration) || !greenConfiguration.isIgnore();

        if(realCall) {
            return method.invoke(originalBean , args);
        }

        processEnergyMetric(method);

        return greenReturnMockValue.getReturnValue(method, greenConfiguration);
    }

    private void processEnergyMetric(Method method) {
        String key = String.format("%s#%s", method.getDeclaringClass().getName(), method.getName());

        Double savedValue = Arrays.stream(method.getAnnotations())
                .map(annotation -> GreenEnergyMetricsProcessor.getInstance().getMetricProcessor(annotation.annotationType()))
                .filter(Objects::nonNull)
                .map(processor -> processor.processEnergyEstimation(method))
                .findFirst()
                .orElse(0.0);

        greenMetricService.save(savedValue, key);
    }
}
