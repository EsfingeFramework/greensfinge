package br.com.ita.greenframework.configuration;

import br.com.ita.greenframework.configuration.esfinge.dto.ClassContainer;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.configuration.interceptorprocessor.GreenStrategyProcessor;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class GreenMethodInterceptor {

    private final ClassContainer classContainer;

    public GreenMethodInterceptor (ClassContainer classContainer) {
        this.classContainer = classContainer;
    }

    @RuntimeType
    public Object intercept(@This Object target, @AllArguments Object[] args, @Origin Method method, @SuperCall Callable<?> zuper) throws Exception {
        System.out.println("Before method execution: " + method.toGenericString());

        for (ContainerField containerField : classContainer.getFields()) {
            if(containerField.isHasGreenAnnotation()) {

                Field field = target.getClass().getSuperclass().getDeclaredField(containerField.getAttributeName());

                GreenStrategyProcessor strategyProcessor = GreenStrategyProcessor.getInstance()
                        .getProcessor(containerField.getAnnotationField());
                strategyProcessor.process(field, containerField, target);
            }
        }

        Object result = zuper.call();

        System.out.println("After method execution: " + method.toGenericString());

        return result;
    }
}
