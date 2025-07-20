package net.sf.esfinge.greenframework.core.configuration;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.*;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.core.service.GreenProxyResolverService;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import static net.sf.esfinge.greenframework.core.util.GreenConstant.FIELD_ORIGINAL_BEAN;

@Slf4j
public class GreenMethodInterceptor {

    private final ContainerField containerField;
    private final GreenProxyResolverService greenProxyResolverService;

    public GreenMethodInterceptor(ContainerField containerField) {
        this.containerField = containerField;
        greenProxyResolverService = new GreenProxyResolverService();
    }

    @RuntimeType
    public Object intercept(@This Object target, @AllArguments Object[] args, @Origin Method method, @SuperCall Callable<?> zuper) throws Exception {
        log.debug("Intercepted method: {}#{} ", method.getDeclaringClass(), method.getName());

        Field originalBean = target.getClass().getDeclaredField(FIELD_ORIGINAL_BEAN);

        return greenProxyResolverService.resolveMethodInterceptCall(originalBean.get(target), method, args, containerField);
    }

}
