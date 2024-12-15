package br.com.ita.greenframework.configuration;

import br.com.ita.greenframework.annotation.GreenReturnWhenSwitchOff;
import br.com.ita.greenframework.configuration.esfinge.dto.ClassContainer;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.configuration.interceptorprocessor.GreenStrategyProcessor;
import br.com.ita.greenframework.configuration.mockprocessor.GreenReturnMockValue;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
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
    public Object intercept(@This Object target, @Origin Method method, @SuperCall Callable<?> zuper) throws Exception {
        log.debug("Before field execution: {}", method.toGenericString());

        interceptGreenFieldsAnnotations(target);
        Object result = interceptGreenMethodAnnotations(method, zuper);

        log.debug("After field execution: {}", method.toGenericString());

        return result;
    }

    private Object interceptGreenMethodAnnotations(Method method, Callable<?> zuper) throws Exception {
        for (Annotation annotation : method.getAnnotations()) {
            if(GreenReturnWhenSwitchOff.class.equals(annotation.annotationType())) {
                return greenReturnMockValue.getReturnValue(method);
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