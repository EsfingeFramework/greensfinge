package br.com.ita.greenframework.configuration;

import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.configuration.mockprocessor.GreenMockProcessor;
import br.com.ita.greenframework.configuration.mockprocessor.GreenReturnMockValue;
import br.com.ita.greenframework.dto.annotation.GreenOptionalConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;

@Slf4j
public class GreenMethodInterceptor {

    private final ContainerField containerField;
    private final GreenReturnMockValue greenReturnMockValue;

    public GreenMethodInterceptor(ContainerField containerField) {
        this.containerField = containerField;
        greenReturnMockValue = new GreenReturnMockValue(containerField);
    }

    @RuntimeType
    public Object intercept(@This Object target, @AllArguments Object[] args, @Origin Method method, @SuperCall Callable<?> zuper) {
        log.debug("Intercepted method: {}#{} ", method.getDeclaringClass(), method.getName());

        String configurationKey = (String) containerField.getAnnotationValue().get("configurationKey");
        GreenOptionalConfiguration configuration = GreenThreadLocal.getValue(configurationKey);

        if(Objects.isNull(configuration) || !configuration.isIgnore()) {
            return invokeMethod(method, args, target);
        } else {
            for (Annotation annotation : method.getAnnotations()) {
                Optional.ofNullable(GreenMockProcessor.getInstance().getProcessor(annotation.annotationType()))
                        .ifPresent(processor -> processor.process(method, containerField));
            }

            return greenReturnMockValue.getReturnValue(method);
        }
    }

    @SneakyThrows
    private Object invokeMethod(Method method, Object[] args, Object target) {
        //TODO Better code for when you have several constructors
        Object objectInvoke = target.getClass().getSuperclass().getDeclaredConstructor().newInstance();
        try {
            return method.invoke(objectInvoke, args);
        } catch (Exception e) {
            throw e.getCause();
        }
    }
}
