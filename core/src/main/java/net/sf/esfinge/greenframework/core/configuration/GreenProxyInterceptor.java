package net.sf.esfinge.greenframework.core.configuration;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.*;
import net.sf.esfinge.greenframework.core.annotation.GreenDefaultReturn;
import net.sf.esfinge.greenframework.core.annotation.GreenSwitchOff;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ClassContainer;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.core.configuration.interceptorprocessor.GreenStrategyProcessor;
import net.sf.esfinge.greenframework.core.service.GreenProxyResolverService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.Callable;

import static net.sf.esfinge.greenframework.core.util.GreenConstant.FIELD_ORIGINAL_BEAN;

@Slf4j
public class GreenProxyInterceptor {

    private final ClassContainer classContainer;
    private final GreenProxyResolverService greenProxyResolverService;

    protected GreenProxyInterceptor(ClassContainer classContainer) {
        this.classContainer = classContainer;
        this.greenProxyResolverService = new GreenProxyResolverService();
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
                //Dynamic attribute created from GreenFactory
                Field originalBean = target.getClass().getDeclaredField(FIELD_ORIGINAL_BEAN);

                return greenProxyResolverService.resolveMethodInterceptCall(originalBean.get(target), method, args, null);
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