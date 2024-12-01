package br.com.ita.greenframework.configuration.mockprocessor;

import br.com.ita.greenframework.annotation.GreenDefaultReturn;
import br.com.ita.greenframework.configuration.GreenThreadLocal;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.dto.annotation.GreenOptionalConfiguration;
import br.com.ita.greenframework.util.GreenConstant;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static br.com.ita.greenframework.util.GreenConstant.CONFIGURATION_KEY;

@Slf4j
public class GreenReturnMockValue {

    private static final List<Class<?>> SIMPLE_TYPES = Arrays.asList(
            String.class, Integer.class, Double.class, Boolean.class, Long.class,
            Short.class, Float.class, Byte.class, Character.class, Void.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ContainerField containerField;


    public GreenReturnMockValue(ContainerField containerField) {
        this.containerField = containerField;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Object getReturnValue(Method method) {
        GreenDefaultReturn greenDefaultReturn = method.getAnnotation(GreenDefaultReturn.class);

        if(Objects.isNull(greenDefaultReturn)) {
            log.debug("The {}#{} method his mocked, but does not contain the @GreenDefaultValue annotation",
                    method.getDeclaringClass().getName(), method.getName());
        }

        return processReturnByType(method, greenDefaultReturn);
    }

    private Object processReturnByType(Method method, GreenDefaultReturn greenDefaultReturn) {
        if(isPrimitiveOrWrapper(method.getReturnType())) {
            return processReturnMockValue(method,greenDefaultReturn);
        } else {
            return getObjectMockValue(greenDefaultReturn, method.getReturnType());
        }
    }

    private Object processReturnMockValue(Method method, GreenDefaultReturn greenDefaultReturn) {
        if (String.class.equals(method.getReturnType())) {
            return getStrMockValue(greenDefaultReturn);
        } else if (Integer.class.equals(method.getReturnType())) {
            return getIntMockValue(greenDefaultReturn);
        } else if (method.getReturnType().equals(Void.TYPE)) {
            return null;
        } else if (Long.class.equals(method.getReturnType())) {
            return getIntMockValue(greenDefaultReturn).longValue();
        }
        return null;
    }

    @SneakyThrows
    private Object getObjectMockValue(GreenDefaultReturn greenDefaultReturn, Class<?> returnType) {
        String configurationKey = (String) containerField.getAnnotationValue().get(CONFIGURATION_KEY);
        GreenOptionalConfiguration configuration = GreenThreadLocal.getValue(configurationKey);

        if(Objects.nonNull(greenDefaultReturn) && !GreenConstant.STR_DEFAULT_VALUE.equals(greenDefaultReturn.strValue())) {
            return objectMapper.readValue(greenDefaultReturn.strValue(), returnType);
        } else if(Objects.nonNull(configuration)) {
            return objectMapper.readValue(configuration.getStrDefaultValue(), returnType);
        } else {
            return null;
        }
    }

    private boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() || SIMPLE_TYPES.contains(clazz);
    }

    private Integer getIntMockValue(GreenDefaultReturn greenDefaultReturn) {
        String configurationKey = (String) containerField.getAnnotationValue().get(CONFIGURATION_KEY);
        GreenOptionalConfiguration configuration = GreenThreadLocal.getValue(configurationKey);

        if(Objects.isNull(greenDefaultReturn) || Objects.isNull(configuration)) {
            return (int) GreenConstant.DOUBLE_DEFAULT_VALUE;
        } else if(GreenConstant.DOUBLE_DEFAULT_VALUE == greenDefaultReturn.numberValue()) {
            return configuration.getNumberDefaultValue().intValue();
        } else {
            return 0;
        }
    }

    private String getStrMockValue(GreenDefaultReturn greenDefaultReturn) {
        String configurationKey = (String) containerField.getAnnotationValue().get(CONFIGURATION_KEY);
        GreenOptionalConfiguration configuration = GreenThreadLocal.getValue(configurationKey);

        if(Objects.nonNull(greenDefaultReturn) && !GreenConstant.STR_DEFAULT_VALUE.equals(greenDefaultReturn.strValue())) {
            return greenDefaultReturn.strValue();
        } else if(Objects.nonNull(configuration)) {
            return configuration.getStrDefaultValue();
        } else {
            return null;
        }
    }
}
