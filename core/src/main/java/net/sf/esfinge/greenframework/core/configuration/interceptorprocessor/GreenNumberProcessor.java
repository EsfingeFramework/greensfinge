package net.sf.esfinge.greenframework.core.configuration.interceptorprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenAdjustableNumberConfiguration;
import net.sf.esfinge.greenframework.core.service.GreenConfigurationService;
import net.sf.esfinge.greenframework.core.util.GreenReflectionUtil;

import java.lang.reflect.Field;
import java.util.Objects;

public class GreenNumberProcessor extends GreenStrategyProcessor {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GreenConfigurationService configurationService = new GreenConfigurationService();

    @SneakyThrows
    @Override
    public void process(Field field, ContainerField containerField, Object target) {
        GreenConfigKey greenConfigKey = field.getAnnotation(GreenConfigKey.class);
        GreenAdjustableNumberConfiguration configuration = configurationService.getConfigurationByType(greenConfigKey.value(), GreenAdjustableNumberConfiguration.class);

        if (Objects.nonNull(configuration)) {
            Object injectionTarget = GreenReflectionUtil.resolveInjectionTarget(target);
            Object convertValue = objectMapper.convertValue(configuration.getValue() , field.getType());
            GreenReflectionUtil.injectValue(field, injectionTarget, convertValue);
        }
    }

}
