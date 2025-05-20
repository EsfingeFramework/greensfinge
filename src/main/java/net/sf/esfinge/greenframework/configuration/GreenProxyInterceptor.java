package net.sf.esfinge.greenframework.configuration;

import net.sf.esfinge.greenframework.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.annotation.GreenDefaultReturn;
import net.sf.esfinge.greenframework.annotation.GreenSwitchOff;
import net.sf.esfinge.greenframework.configuration.esfinge.dto.ClassContainer;
import net.sf.esfinge.greenframework.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.configuration.interceptorprocessor.GreenStrategyProcessor;
import net.sf.esfinge.greenframework.configuration.mockprocessor.GreenReturnMockValue;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.*;
import net.sf.esfinge.greenframework.dto.annotation.GreenSwitchConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;

@Slf4j
public class GreenProxyInterceptor {

    private final ClassContainer classContainer;
    private final GreenReturnMockValue greenReturnMockValue;

    protected GreenProxyInterceptor(ClassContainer classContainer) {
        this.classContainer = classContainer;
        greenReturnMockValue = new GreenReturnMockValue();
    }

    @RuntimeType
    public Object intercept(@This Object target, @AllArguments Object[] args, @Origin Method method, @SuperCall Callable<?> zuper) throws Exception {
        log.debug("Before field execution: {}", method.toGenericString());

        interceptGreenFieldsAnnotations(target);
        Object result = interceptGreenMethodAnnotations(target, args, method, zuper);

        log.debug("After field execution: {}", method.toGenericString());

        return result;
    }

    private Object interceptGreenMethodAnnotations(Object target, Object[] args, Method method, Callable<?> zuper) throws Exception {
        for (Annotation annotation : method.getAnnotations()) {
            if(GreenDefaultReturn.class.equals(annotation.annotationType()) || GreenSwitchOff.class.equals(annotation.annotationType())) {
                GreenConfigKey configKey = method.getAnnotation(GreenConfigKey.class);
                GreenDefaultReturn greenDefaultReturn = method.getAnnotation(GreenDefaultReturn.class);

                if(Objects.isNull(configKey)) {
                    log.debug("The {}#{} method his mocked, but does not contain the @GreenConfigKey annotation",
                            method.getDeclaringClass().getName(), method.getName());
                } else {
                    GreenSwitchConfiguration configuration = GreenThreadLocal.getValue(configKey.value());
                    if(Objects.isNull(configuration)) {
                        return GreenMethodInterceptor.invokeMethod(method, args, target);
                    } else if (configuration.isIgnore()) {
                        return greenReturnMockValue.getReturnValueByMethod(configKey, greenDefaultReturn, method);
                    } else {
                        return GreenMethodInterceptor.invokeMethod(method, args, target);
                    }
                }
            }
        }

        return zuper.call();
    }

    @SneakyThrows
    private void interceptGreenFieldsAnnotations(Object target) {
        for (ContainerField containerField : classContainer.getFields()) {
            Field field = target.getClass().getSuperclass().getDeclaredField(containerField.getAttributeName());

            containerField.getAnnotationField().forEach(annotationField -> {
                GreenStrategyProcessor strategyProcessor = GreenStrategyProcessor.getInstance()
                        .getProcessor(annotationField);

                Optional.ofNullable(strategyProcessor)
                        .ifPresent(processor -> processor.process(field, containerField, target));

            });
        }
    }

}