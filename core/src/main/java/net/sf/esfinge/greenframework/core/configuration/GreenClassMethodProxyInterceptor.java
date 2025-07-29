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
import net.sf.esfinge.greenframework.core.util.GreenReflectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.Callable;

import static net.sf.esfinge.greenframework.core.util.GreenConstant.FIELD_ORIGINAL_BEAN;

@Slf4j
public class GreenClassMethodProxyInterceptor {

    private final GreenProxyResolverService greenProxyResolverService = new GreenProxyResolverService();
    private final ClassContainer classContainer;

    public GreenClassMethodProxyInterceptor(ClassContainer classContainer) {
        this.classContainer = classContainer;
    }

    @RuntimeType
    public Object intercept(@This Object target, @AllArguments Object[] args, @Origin Method method, @SuperCall Callable<?> zuper) throws Exception {
        log.debug("Before class method execution: {}", method.toGenericString());

        interceptGreenFieldsAnnotations(target);
        Object result = interceptGreenMethodAnnotations(target, args, method);

        log.debug("After class method execution: {}", method.toGenericString());
        return result;
    }

    @SneakyThrows
    private Object interceptGreenMethodAnnotations(Object target, Object[] args, Method method) {
        //Dynamic attribute created from GreenFactory
        Field originalBean = target.getClass().getDeclaredField(FIELD_ORIGINAL_BEAN);

        for (Annotation annotation : method.getAnnotations()) {
            if(GreenSwitchOff.class.equals(annotation.annotationType())) {
                return greenProxyResolverService.resolveMethodInterceptCall(originalBean.get(target), method, args, null);
            }
        }

        //Need to invoke the original bean for the other attribute dependencies
        return method.invoke(originalBean.get(target), args);
    }

    @SneakyThrows
    private void interceptGreenFieldsAnnotations(Object target) {
        for (ContainerField containerField : classContainer.getFields()) {
            Object resolvedTarget = GreenReflectionUtil.resolveInjectionTarget(target);
            Field field = resolvedTarget.getClass().getDeclaredField(containerField.getAttributeName());

            containerField.getAnnotationField().forEach(annotationField -> {
                GreenStrategyProcessor strategyProcessor = GreenStrategyProcessor.getInstance()
                        .getProcessor(annotationField);

                Optional.ofNullable(strategyProcessor)
                        .ifPresent(processor -> processor.process(field, containerField, target));
            });
        }
    }

}