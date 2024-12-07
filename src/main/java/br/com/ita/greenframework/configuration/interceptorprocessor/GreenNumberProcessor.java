package br.com.ita.greenframework.configuration.interceptorprocessor;

import br.com.ita.greenframework.annotation.GreenConfigKey;
import br.com.ita.greenframework.configuration.GreenThreadLocal;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.dto.annotation.GreenAdjustableNumberConfiguration;
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
