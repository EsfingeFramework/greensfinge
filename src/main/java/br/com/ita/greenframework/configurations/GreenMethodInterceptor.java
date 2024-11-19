package br.com.ita.greenframework.configurations;

import br.com.ita.greenframework.configurations.esfinge.dto.ClassContainer;
import br.com.ita.greenframework.configurations.esfinge.dto.ContainerField;
import br.com.ita.greenframework.configurations.interceptorprocessor.GreenStrategyProcessor;
import net.bytebuddy.implementation.bind.annotation.*;
import net.sf.esfinge.metadata.AnnotationReader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GreenMethodInterceptor {

    @RuntimeType
    public Object intercept(@This Object target, @AllArguments Object[] args, @Origin Method method, @SuperCall Callable<?> zuper) throws Exception {
        System.out.println("Before method execution: " + method.toGenericString());

        AnnotationReader reader = new AnnotationReader();
        ClassContainer classContainer = reader.readingAnnotationsTo(target.getClass().getSuperclass(), ClassContainer.class);

        List<Field> declaredFields = Stream.of(target.getClass().getSuperclass().getDeclaredFields())
                .collect(Collectors.toList());

        for (ContainerField containerField : classContainer.getFields()) {
            if(containerField.isHasGreenAnnotation()) {

                Field field = declaredFields.stream().filter(e -> e.getName().equals(containerField.getAttributeName()))
                        .findFirst()
                        .orElseGet(null);

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
