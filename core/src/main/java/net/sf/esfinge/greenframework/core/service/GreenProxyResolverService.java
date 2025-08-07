package net.sf.esfinge.greenframework.core.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.core.configuration.energyestimation.GreenEnergyMetricsProcessor;
import net.sf.esfinge.greenframework.core.configuration.mockreturn.GreenReturnMockValue;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.core.dto.project.ResolverMetricDTO;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class GreenProxyResolverService {

    protected final GreenMetricService greenMetricService = new GreenMetricService();
    private final GreenConfigurationService configurationService = new GreenConfigurationService();
    private final GreenReturnMockValue greenReturnMockValue = new GreenReturnMockValue();
    private final GreenCustomMockService greenCustomMockService = new GreenCustomMockService();

    @SneakyThrows
    public Object resolveMethodInterceptCall(Object originalBean, Method method, Object[] args, ContainerField containerField) {
        GreenSwitchConfiguration greenConfiguration = null;
        GreenConfigKey configKey = method.getAnnotation(GreenConfigKey.class);
        GreenCustomMockConfiguration customMockConfiguration = greenCustomMockService.findByReturnType(method.getReturnType().getTypeName());

        if(Objects.isNull(configKey)) {
            log.debug("The {}#{} method his mocked, but does not contain the @GreenConfigKey annotation",
                    method.getDeclaringClass().getName(), method.getName());
        } else {
            greenConfiguration = configurationService.getConfigurationByType(configKey.value(), GreenSwitchConfiguration.class);
        }

        return processResolveMethodIntercept(configKey , greenConfiguration, originalBean, args, customMockConfiguration, method);
    }

    @SneakyThrows
    private Object processResolveMethodIntercept(GreenConfigKey configKey, GreenSwitchConfiguration greenConfiguration, Object originalBean,
                                                 Object[] args, GreenCustomMockConfiguration customMockConfiguration, Method method) {
        Object objReturn = null;

        ResolverMetricDTO dto = new ResolverMetricDTO();

        boolean realCall = Objects.isNull(configKey) || Objects.isNull(greenConfiguration) || !greenConfiguration.isIgnore();

        long begin = System.currentTimeMillis();

        if(realCall) {
            objReturn = method.invoke(originalBean , args);
        } else {
            objReturn = greenReturnMockValue.getReturnValue(method, greenConfiguration, customMockConfiguration);
        }

        long end = System.currentTimeMillis();

        dto.setRealCall(realCall);
        dto.setBegin(begin);
        dto.setEnd(end);
        dto.setConfiguration(greenConfiguration);
        dto.setMockConfiguration(customMockConfiguration);

        processEnergyMetric(method, dto);

        return objReturn;

    }

    private void processEnergyMetric(Method method, ResolverMetricDTO dto) {
        String key = String.format("%s#%s", method.getDeclaringClass().getName(), method.getName());

        Double savedValue = Arrays.stream(method.getAnnotations())
                .map(annotation -> GreenEnergyMetricsProcessor.getInstance().getMetricProcessor(annotation.annotationType()))
                .filter(Objects::nonNull)
                .map(processor -> processor.processEnergyEstimation(method))
                .findFirst()
                .orElse(0.0);

        dto.setKey(key);
        dto.setSavedValue(savedValue);

        greenMetricService.save(dto);
    }
}
