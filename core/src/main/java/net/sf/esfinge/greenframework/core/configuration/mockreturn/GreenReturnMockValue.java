package net.sf.esfinge.greenframework.core.configuration.mockreturn;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.greenframework.core.annotation.GreenDefaultReturn;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;

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

    public GreenReturnMockValue() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Object getReturnValue(Method method, GreenSwitchConfiguration greenConfiguration, GreenCustomMockConfiguration customMockConfiguration) {
        GreenDefaultReturn greenDefaultReturn = method.getAnnotation(GreenDefaultReturn.class);

        if (Objects.isNull(greenDefaultReturn)) {
            log.debug("The {}#{} method his mocked, but does not contain the @GreenDefaultReturn annotation",
                    method.getDeclaringClass().getName(), method.getName());
        }

        return processReturnByType(method, greenDefaultReturn, greenConfiguration, customMockConfiguration);
    }

    private Object processReturnByType(Method method, GreenDefaultReturn greenDefaultReturn, GreenSwitchConfiguration greenConfiguration, GreenCustomMockConfiguration customMockConfiguration) {
        if (isPrimitiveOrWrapper(method.getReturnType())) {
            return processReturnMockValue(method, greenDefaultReturn, greenConfiguration);
        } else {
            return getObjectMockValue(greenDefaultReturn, method.getReturnType(), greenConfiguration, customMockConfiguration);
        }
    }

    private Object processReturnMockValue(Method method, GreenDefaultReturn greenDefaultReturn, GreenSwitchConfiguration greenConfiguration) {
        if (String.class.equals(method.getReturnType())) {
            return getStrMockValue(greenDefaultReturn, greenConfiguration);
        } else if (Integer.class.equals(method.getReturnType())) {
            return getIntMockValue(greenDefaultReturn, greenConfiguration);
        } else if (method.getReturnType().equals(Void.TYPE)) {
            return null;
        } else if (Long.class.equals(method.getReturnType())) {
            return Optional.ofNullable(getIntMockValue(greenDefaultReturn, greenConfiguration))
                    .map(Long::valueOf)
                    .orElse(null);
        }
        return null;
    }

    @SneakyThrows
    private Object getObjectMockValue(GreenDefaultReturn greenDefaultReturn, Class<?> returnType, GreenSwitchConfiguration greenConfiguration, GreenCustomMockConfiguration customMockConfiguration) {
        if(Objects.nonNull(greenConfiguration) && Objects.isNull(customMockConfiguration)) {
            return objectMapper.readValue(greenConfiguration.getStrDefaultValue(), returnType);
        }

        if(Objects.nonNull(greenDefaultReturn)) {
            return objectMapper.readValue(greenDefaultReturn.strValue(), returnType);
        }


        if(Objects.nonNull(customMockConfiguration)) {
            return processCustomMockReturn(customMockConfiguration);
        }

        return null;
    }

    @SneakyThrows
    private Object processCustomMockReturn(GreenCustomMockConfiguration customMockConfiguration) {
        Object customMockProvider = Class.forName(customMockConfiguration.getCustomClass())
                .getDeclaredConstructor().newInstance();

        if (customMockProvider instanceof GreenCustomMockProvider provider) {
            Object customMockObject = provider.processCustomMockReturn(customMockConfiguration, customMockConfiguration.toMap());

            Class<?> expectedType = Class.forName(customMockConfiguration.getReturnType());
            if (expectedType.isInstance(customMockObject)) {
                return customMockObject;
            } else {
                throw new IllegalStateException("Returned object is not of expected type: " + expectedType.getName());
            }
        } else {
            throw new IllegalStateException("Classe deve implementar GreenMockProvider");
        }
    }

    private Integer getIntMockValue(GreenDefaultReturn greenDefaultReturn, GreenSwitchConfiguration greenConfiguration) {
        if(Objects.nonNull(greenConfiguration)) {
            return greenConfiguration.getNumberDefaultValue().intValue();
        }

        if(Objects.nonNull(greenDefaultReturn)) {
            return (int) greenDefaultReturn.numberValue();
        }

        return null;
    }

    private String getStrMockValue(GreenDefaultReturn greenDefaultReturn, GreenSwitchConfiguration greenConfiguration) {
        if(Objects.nonNull(greenConfiguration) && Objects.nonNull(greenConfiguration.getStrDefaultValue())) {
            return greenConfiguration.getStrDefaultValue();
        }

        if(Objects.nonNull(greenDefaultReturn)) {
            return greenDefaultReturn.strValue();
        }

        return null;
    }

    private boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() || SIMPLE_TYPES.contains(clazz);
    }

}
