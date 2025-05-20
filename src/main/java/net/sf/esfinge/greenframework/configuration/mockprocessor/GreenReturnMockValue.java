package net.sf.esfinge.greenframework.configuration.mockprocessor;

import net.sf.esfinge.greenframework.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.annotation.GreenDefaultReturn;
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
        GreenDefaultReturn greenDefaultReturn = method.getAnnotation(GreenDefaultReturn.class);

        if (Objects.isNull(greenDefaultReturn)) {
            log.debug("The {}#{} method his mocked, but does not contain the @GreenDefaultReturn annotation",
                    method.getDeclaringClass().getName(), method.getName());
        }

        return processReturnByType(method, greenDefaultReturn);
    }

    private Object processReturnByType(Method method, GreenDefaultReturn greenDefaultReturn) {
        if (isPrimitiveOrWrapper(method.getReturnType())) {
            return processReturnMockValue(method, greenDefaultReturn, null);
        } else {
            return getObjectMockValue(greenDefaultReturn, method.getReturnType());
        }
    }

    private Object processReturnMockValue(Method method, GreenDefaultReturn greenDefaultReturn, GreenSwitchConfiguration methodGreenConfiguration) {
        if (String.class.equals(method.getReturnType())) {
            return getStrMockValue(greenDefaultReturn, methodGreenConfiguration);
        } else if (Integer.class.equals(method.getReturnType())) {
            return getIntMockValue(greenDefaultReturn, methodGreenConfiguration);
        } else if (method.getReturnType().equals(Void.TYPE)) {
            return null;
        } else if (Long.class.equals(method.getReturnType())) {
            return Optional.ofNullable(getIntMockValue(greenDefaultReturn, methodGreenConfiguration))
                    .map(Long::valueOf)
                    .orElse(null);
        }
        return null;
    }

    @SneakyThrows
    private Object getObjectMockValue(GreenDefaultReturn greenDefaultReturn, Class<?> returnType) {
        GreenSwitchConfiguration configuration = getGreenSwitchConfiguration();

        if (hasGreenDefaultAnnotationAndNotDefaultStrValue(greenDefaultReturn)) {
            return objectMapper.readValue(greenDefaultReturn.strValue(), returnType);
        } else if (existsConfigurationAndNotDefaultStrValue(configuration)) {
            return objectMapper.readValue(configuration.getStrDefaultValue(), returnType);
        } else {
            return null;
        }
    }

    private Integer getIntMockValue(GreenDefaultReturn greenDefaultReturn, GreenSwitchConfiguration methodGreenConfiguration) {
        GreenSwitchConfiguration configuration = methodGreenConfiguration;
        if(Objects.isNull(methodGreenConfiguration)) {
            configuration = getGreenSwitchConfiguration();
        }

        if (hasGreenDefaultAnnotationAndNotDefaultNumberValue(greenDefaultReturn)) {
            return (int) greenDefaultReturn.numberValue();
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

    public Object getReturnValueByMethod(GreenConfigKey configKey, GreenDefaultReturn greenDefaultReturn, Method method) {
        if (configKey != null) {
            GreenSwitchConfiguration configuration = GreenThreadLocal.getValue(configKey.value());
            return processReturnMockValue(method, greenDefaultReturn, configuration);
        }
        return null;
    }

    private String getStrMockValue(GreenDefaultReturn greenDefaultReturn, GreenSwitchConfiguration methodGreenConfiguration) {
        GreenSwitchConfiguration configuration = methodGreenConfiguration;
        if(Objects.isNull(methodGreenConfiguration)) {
            configuration = getGreenSwitchConfiguration();
        }

        if (hasGreenDefaultAnnotationAndNotDefaultStrValue(greenDefaultReturn)) {
            return greenDefaultReturn.strValue();
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

    private boolean hasGreenDefaultAnnotationAndNotDefaultStrValue(GreenDefaultReturn greenDefaultReturn) {
        return Objects.nonNull(greenDefaultReturn) && !GreenConstant.STR_DEFAULT_VALUE.equals(greenDefaultReturn.strValue());
    }

    private boolean hasGreenDefaultAnnotationAndNotDefaultNumberValue(GreenDefaultReturn greenDefaultReturn) {
        return Objects.nonNull(greenDefaultReturn) && GreenConstant.DOUBLE_DEFAULT_VALUE != greenDefaultReturn.numberValue();
    }

    private boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() || SIMPLE_TYPES.contains(clazz);
    }

}
