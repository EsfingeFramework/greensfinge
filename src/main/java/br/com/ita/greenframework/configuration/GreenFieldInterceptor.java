package br.com.ita.greenframework.configuration;

import br.com.ita.greenframework.configuration.esfinge.dto.ClassContainer;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.configuration.interceptorprocessor.GreenStrategyProcessor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@Slf4j
public class GreenFieldInterceptor {

    private final ClassContainer classContainer;

    public GreenFieldInterceptor(ClassContainer classContainer) {
        this.classContainer = classContainer;
    }

    @RuntimeType
    public Object intercept(@This Object target, @AllArguments Object[] args, @Origin Method method, @SuperCall Callable<?> zuper) throws Exception {
        log.debug("Before field execution: " + method.toGenericString());

        for (ContainerField containerField : classContainer.getFields()) {
            Field field = target.getClass().getSuperclass().getDeclaredField(containerField.getAttributeName());

            GreenStrategyProcessor strategyProcessor = GreenStrategyProcessor.getInstance()
                    .getProcessor(containerField.getAnnotationField());
            strategyProcessor.process(field, containerField, target);
        }

        Object result = zuper.call();

        log.debug("After field execution: " + method.toGenericString());

        return result;
    }
}
