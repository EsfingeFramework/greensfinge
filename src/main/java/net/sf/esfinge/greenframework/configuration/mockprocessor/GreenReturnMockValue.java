package net.sf.esfinge.greenframework.configuration.mockprocessor;

import net.sf.esfinge.greenframework.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.annotation.GreenReturnWhenSwitchOff;
import net.sf.esfinge.greenframework.configuration.GreenThreadLocal;
import net.sf.esfinge.greenframework.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.util.GreenConstant;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class GreenReturnMockValue {

    private static final List<Class<?>> SIMPLE_TYPES = Arrays.asList(
            String.class, Integer.class, Double.class, Boolean.class, Long.class,
            Short.class, Float.class, Byte.class, Character.class, Void.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ContainerField containerField = null;

    public GreenReturnMockValue() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public GreenReturnMockValue(ContainerField containerField) {
        this.containerField = containerField;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Object getReturnValue(Method method) {
        GreenReturnWhenSwitchOff greenReturnWhenSwitchOff = method.getAnnotation(GreenReturnWhenSwitchOff.class);

        if (Objects.isNull(greenReturnWhenSwitchOff)) {
            log.debug("The {}#{} method his mocked, but does not contain the @GreenDefaultValue annotation",
                    method.getDeclaringClass().getName(), method.getName());
        }

        return processReturnByType(method, greenReturnWhenSwitchOff);
    }

    private Object processReturnByType(Method method, GreenReturnWhenSwitchOff greenReturnWhenSwitchOff) {
        if (isPrimitiveOrWrapper(method.getReturnType())) {
            return processReturnMockValue(method, greenReturnWhenSwitchOff, null);
        } else {
            return getObjectMockValue(greenReturnWhenSwitchOff, method.getReturnType());
        }
    }

    private Object processReturnMockValue(Method method, GreenReturnWhenSwitchOff greenReturnWhenSwitchOff, GreenSwitchConfiguration methodGreenConfiguration) {
        if (String.class.equals(method.getReturnType())) {
            return getStrMockValue(greenReturnWhenSwitchOff, methodGreenConfiguration);
        } else if (Integer.class.equals(method.getReturnType())) {
            return getIntMockValue(greenReturnWhenSwitchOff, methodGreenConfiguration);
        } else if (method.getReturnType().equals(Void.TYPE)) {
            return null;
        } else if (Long.class.equals(method.getReturnType())) {
            return Optional.ofNullable(getIntMockValue(greenReturnWhenSwitchOff, methodGreenConfiguration))
                    .map(Long::valueOf)
                    .orElse(null);
        }
        return null;
    }

    @SneakyThrows
    private Object getObjectMockValue(GreenReturnWhenSwitchOff greenReturnWhenSwitchOff, Class<?> returnType) {
        GreenSwitchConfiguration configuration = getGreenSwitchConfiguration();

        if (hasGreenDefaultAnnotationAndNotDefaultStrValue(greenReturnWhenSwitchOff)) {
            return objectMapper.readValue(greenReturnWhenSwitchOff.strValue(), returnType);
        } else if (existsConfigurationAndNotDefaultStrValue(configuration)) {
            return objectMapper.readValue(configuration.getStrDefaultValue(), returnType);
        } else {
            return null;
        }
    }

    private Integer getIntMockValue(GreenReturnWhenSwitchOff greenReturnWhenSwitchOff, GreenSwitchConfiguration methodGreenConfiguration) {
        GreenSwitchConfiguration configuration = methodGreenConfiguration;
        if(Objects.isNull(methodGreenConfiguration)) {
            configuration = getGreenSwitchConfiguration();
        }

        if (hasGreenDefaultAnnotationAndNotDefaultNumberValue(greenReturnWhenSwitchOff)) {
            return (int) greenReturnWhenSwitchOff.numberValue();
        } else if (existsConfigurationAndNotDefaultNumberValue(configuration)) {
            return configuration.getNumberDefaultValue().intValue();
        } else {
            return null;
        }
    }

    private GreenSwitchConfiguration getGreenSwitchConfiguration() {
        return (GreenSwitchConfiguration) Optional.ofNullable(containerField)
                .map(e -> {
                    String configurationKey = (String) containerField.getAnnotationValue().get(GreenConstant.GREEN_KEY_VALUE);
                    return GreenThreadLocal.getValue(configurationKey);
                })
                .orElse(null);
    }

    public Object getReturnValueByMethod(GreenConfigKey configKey, GreenReturnWhenSwitchOff greenReturnWhenSwitchOff, Method method) {
        if (configKey != null) {
            GreenSwitchConfiguration configuration = GreenThreadLocal.getValue(configKey.value());
            return processReturnMockValue(method, greenReturnWhenSwitchOff, configuration);
        }
        return null;
    }

    private String getStrMockValue(GreenReturnWhenSwitchOff greenReturnWhenSwitchOff, GreenSwitchConfiguration methodGreenConfiguration) {
        GreenSwitchConfiguration configuration = methodGreenConfiguration;
        if(Objects.isNull(methodGreenConfiguration)) {
            configuration = getGreenSwitchConfiguration();
        }

        if (hasGreenDefaultAnnotationAndNotDefaultStrValue(greenReturnWhenSwitchOff)) {
            return greenReturnWhenSwitchOff.strValue();
        } else if (existsConfigurationAndNotDefaultStrValue(configuration)) {
            return configuration.getStrDefaultValue();
        } else {
            return null;
        }
    }

    private boolean existsConfigurationAndNotDefaultStrValue(GreenSwitchConfiguration configuration) {
        return Objects.nonNull(configuration) && !GreenConstant.STR_DEFAULT_VALUE.equals(configuration.getStrDefaultValue());
    }

    private boolean existsConfigurationAndNotDefaultNumberValue(GreenSwitchConfiguration configuration) {
        return Objects.nonNull(configuration) && GreenConstant.DOUBLE_DEFAULT_VALUE != configuration.getNumberDefaultValue();
    }

    private boolean hasGreenDefaultAnnotationAndNotDefaultStrValue(GreenReturnWhenSwitchOff greenReturnWhenSwitchOff) {
        return Objects.nonNull(greenReturnWhenSwitchOff) && !GreenConstant.STR_DEFAULT_VALUE.equals(greenReturnWhenSwitchOff.strValue());
    }

    private boolean hasGreenDefaultAnnotationAndNotDefaultNumberValue(GreenReturnWhenSwitchOff greenReturnWhenSwitchOff) {
        return Objects.nonNull(greenReturnWhenSwitchOff) && GreenConstant.DOUBLE_DEFAULT_VALUE != greenReturnWhenSwitchOff.numberValue();
    }

    private boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() || SIMPLE_TYPES.contains(clazz);
    }

}
