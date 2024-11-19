package br.com.ita.greenframework.configuration.interceptorprocessor;

import br.com.ita.greenframework.annotation.GreenNumberConfig;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.dto.GreenNumberConfiguration;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class GreenNumberConfigProcessor extends GreenStrategyProcessor{

    @SneakyThrows
    @Override
    public void process(Field field, ContainerField containerField, Object target) {
        GreenNumberConfig annotation = field.getAnnotation(GreenNumberConfig.class);
        GreenNumberConfiguration configuration = getThreadLocalConfiguration(annotation.configurationKey());

        field.setAccessible(true);
        field.set(target, configuration.getValue());
    }
}
