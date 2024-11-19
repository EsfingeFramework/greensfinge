package br.com.ita.greenframework.configurations.interceptorprocessor;

import br.com.ita.greenframework.configurations.esfinge.dto.ContainerField;

import java.lang.reflect.Field;

public class GreenNumberConfigProcessor extends GreenStrategyProcessor{

    @Override
    public void process(Field field, ContainerField containerField, Object target) {
//        GreenOptional annotation = field.getAnnotation(GreenOptional.class);
//        GreenOptionalConfiguration configuration = getThreadLocalConfiguration(annotation.configurationKey());
    }
}
