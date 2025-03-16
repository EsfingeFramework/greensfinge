package net.sf.esfinge.greenframework.configuration.interceptorprocessor;

import net.sf.esfinge.greenframework.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.configuration.GreenThreadLocal;
import net.sf.esfinge.greenframework.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.dto.annotation.GreenAdjustableNumberConfiguration;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Objects;

public class GreenNumberProcessor extends GreenStrategyProcessor {

    @SneakyThrows
    @Override
    public void process(Field field, ContainerField containerField, Object target) {
        GreenConfigKey greenConfigKey = field.getAnnotation(GreenConfigKey.class);
        GreenAdjustableNumberConfiguration configuration = GreenThreadLocal.getValue(greenConfigKey.value());

        if(Objects.nonNull(configuration)) {
            setReflectionValue(field, target, configuration.getValue());
        }
    }

}
