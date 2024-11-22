package br.com.ita.greenframework.configuration.interceptorprocessor;

import br.com.ita.greenframework.annotation.GreenNumber;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.dto.annotation.GreenNumberConfiguration;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class GreenNumberProcessor extends GreenStrategyProcessor {

    @SneakyThrows
    @Override
    public void process(Field field, ContainerField containerField, Object target) {
        GreenNumber annotation = field.getAnnotation(GreenNumber.class);
        GreenNumberConfiguration configuration = getThreadLocalConfiguration(annotation.configurationKey());

        setReflectionValue(field, target, configuration.getValue());
    }

}
