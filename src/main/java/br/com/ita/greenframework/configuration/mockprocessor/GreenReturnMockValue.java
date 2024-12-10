package br.com.ita.greenframework.configuration.mockprocessor;

import br.com.ita.greenframework.annotation.GreenReturnWhenSwitchOff;
import br.com.ita.greenframework.configuration.GreenThreadLocal;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.dto.annotation.GreenSwitchConfiguration;
import br.com.ita.greenframework.util.GreenConstant;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.com.ita.greenframework.util.GreenConstant.GREEN_KEY_VALUE;

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
        GreenReturnWhenSwitchOff greenReturnWhenSwitchOff = method.getAnnotation(GreenReturnWhenSwitchOff.class);

        if(Objects.isNull(greenReturnWhenSwitchOff)) {
            log.debug("The {}#{} method his mocked, but does not contain the @GreenDefaultValue annotation",
                    method.getDeclaringClass().getName(), method.getName());
        }

        return processReturnByType(method, greenReturnWhenSwitchOff);
    }

    private Object processReturnByType(Method method, GreenReturnWhenSwitchOff greenReturnWhenSwitchOff) {
        if(isPrimitiveOrWrapper(method.getReturnType())) {
            return processReturnMockValue(method, greenReturnWhenSwitchOff);
        } else {
            return getObjectMockValue(greenReturnWhenSwitchOff, method.getReturnType());
        }
    }

    private Object processReturnMockValue(Method method, GreenReturnWhenSwitchOff greenReturnWhenSwitchOff) {
        if (String.class.equals(method.getReturnType())) {
            return getStrMockValue(greenReturnWhenSwitchOff);
        } else if (Integer.class.equals(method.getReturnType())) {
            return getIntMockValue(greenReturnWhenSwitchOff);
        } else if (method.getReturnType().equals(Void.TYPE)) {
            return null;
        } else if (Long.class.equals(method.getReturnType())) {
            return Optional.ofNullable(getIntMockValue(greenReturnWhenSwitchOff))
                    .map(Long::valueOf)
                    .orElse(null);
        }
        return null;
    }

    @SneakyThrows
    private Object getObjectMockValue(GreenReturnWhenSwitchOff greenReturnWhenSwitchOff, Class<?> returnType) {
        String configurationKey = (String) containerField.getAnnotationValue().get(GREEN_KEY_VALUE);
        GreenSwitchConfiguration configuration = GreenThreadLocal.getValue(configurationKey);

        if(hasGreenDefaultAnnotationAndNotDefaultValue(greenReturnWhenSwitchOff)) {
            return objectMapper.readValue(greenReturnWhenSwitchOff.strValue(), returnType);
        } else if(Objects.nonNull(configuration)) {
            return objectMapper.readValue(configuration.getStrDefaultValue(), returnType);
        } else {
            return null;
        }
    }

    private Integer getIntMockValue(GreenReturnWhenSwitchOff greenReturnWhenSwitchOff) {
        String configurationKey = (String) containerField.getAnnotationValue().get(GREEN_KEY_VALUE);
        GreenSwitchConfiguration configuration = GreenThreadLocal.getValue(configurationKey);

        if(Objects.nonNull(configuration) && GreenConstant.DOUBLE_DEFAULT_VALUE != configuration.getNumberDefaultValue()) {
            return configuration.getNumberDefaultValue().intValue();
        } else if(Objects.nonNull(greenReturnWhenSwitchOff)) {
            return(int) greenReturnWhenSwitchOff.numberValue();
        } else {
            return (int) GreenConstant.DOUBLE_DEFAULT_VALUE;
        }
    }

    private String getStrMockValue(GreenReturnWhenSwitchOff greenReturnWhenSwitchOff) {
        String configurationKey = (String) containerField.getAnnotationValue().get(GREEN_KEY_VALUE);
        GreenSwitchConfiguration configuration = GreenThreadLocal.getValue(configurationKey);

        if(hasGreenDefaultAnnotationAndNotDefaultValue(greenReturnWhenSwitchOff)) {
            return greenReturnWhenSwitchOff.strValue();
        } else if(existsConfiguration(configuration)) {
            return configuration.getStrDefaultValue();
        } else {
            return null;
        }
    }

    private boolean existsConfiguration(GreenSwitchConfiguration configuration) {
        return Objects.nonNull(configuration);
    }

    private boolean hasGreenDefaultAnnotationAndNotDefaultValue(GreenReturnWhenSwitchOff greenReturnWhenSwitchOff) {
        return Objects.nonNull(greenReturnWhenSwitchOff) && !GreenConstant.STR_DEFAULT_VALUE.equals(greenReturnWhenSwitchOff.strValue());
    }

    private boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() || SIMPLE_TYPES.contains(clazz);
    }
}
