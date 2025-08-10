package net.sf.esfinge.greenframework.core.configuration.mockreturn;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.greenframework.core.annotation.GreenDefaultReturn;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.core.util.GreenConstant;
import net.sf.esfinge.greenframework.core.util.StringUtil;

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
            return Optional.ofNullable(getDoubleMockValue(greenDefaultReturn, greenConfiguration))
                    .map(Double::longValue)
                    .orElse(null);
        }
        return null;
    }

    @SneakyThrows
    private Object getObjectMockValue(GreenDefaultReturn greenDefaultReturn, Class<?> returnType, GreenSwitchConfiguration greenConfiguration, GreenCustomMockConfiguration customMockConfiguration) {
        if(Objects.nonNull(greenConfiguration) && !GreenConstant.STR_DEFAULT_VALUE.equals(greenConfiguration.getDefaultValue())) {
            if(Objects.isNull(customMockConfiguration)) {
                return objectMapper.readValue(greenConfiguration.getDefaultValue(), returnType);
            } else {
                return processCustomMockReturn(customMockConfiguration);
            }
        }

        if(Objects.nonNull(greenDefaultReturn)) {
            return objectMapper.readValue(greenDefaultReturn.strValue(), returnType);
        }

        return null;
    }

    @SneakyThrows
    private Object processCustomMockReturn(GreenCustomMockConfiguration customMockConfiguration) {
        GreenCustomMockProvider provider = (GreenCustomMockProvider) Class.forName(customMockConfiguration.getCustomClass())
                .getDeclaredConstructor().newInstance();

        return provider.processCustomMockReturn(customMockConfiguration, customMockConfiguration.toMap());
    }

    private Double getDoubleMockValue(GreenDefaultReturn greenDefaultReturn, GreenSwitchConfiguration greenConfiguration) {
        if(Objects.nonNull(greenConfiguration) && StringUtil.isNumber(greenConfiguration.getDefaultValue()) &&
                GreenConstant.DOUBLE_DEFAULT_VALUE != Double.parseDouble(greenConfiguration.getDefaultValue())) {
            return Double.valueOf(greenConfiguration.getDefaultValue());
        }

        if(Objects.nonNull(greenDefaultReturn)) {
            return (double) greenDefaultReturn.numberValue();
        }

        return null;
    }

    private Integer getIntMockValue(GreenDefaultReturn greenDefaultReturn, GreenSwitchConfiguration greenConfiguration) {
        if(Objects.nonNull(greenConfiguration) && StringUtil.isNumber(greenConfiguration.getDefaultValue()) &&
                GreenConstant.DOUBLE_DEFAULT_VALUE != Double.parseDouble(greenConfiguration.getDefaultValue())) {
            return Integer.valueOf(greenConfiguration.getDefaultValue());
        }

        if(Objects.nonNull(greenDefaultReturn)) {
            return (int) greenDefaultReturn.numberValue();
        }

        return null;
    }

    private String getStrMockValue(GreenDefaultReturn greenDefaultReturn, GreenSwitchConfiguration greenConfiguration) {
        if(Objects.nonNull(greenConfiguration) && !GreenConstant.STR_DEFAULT_VALUE.equals(greenConfiguration.getDefaultValue())) {
            return greenConfiguration.getDefaultValue();
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
