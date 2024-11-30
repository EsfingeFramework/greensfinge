package br.com.ita.greenframework.configuration.interceptorprocessor;

import br.com.ita.greenframework.configuration.GreenMethodInterceptor;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Field;

public class GreenOptionalProcessor extends GreenStrategyProcessor {

    @SneakyThrows
    @Override
    public void process(Field field, ContainerField containerField, Object target) {
        Class<?> fieldClass = Class.forName(field.getGenericType().getTypeName());

        Object mockObject = new ByteBuddy()
                .subclass(fieldClass)
                .method(ElementMatchers.isDeclaredBy(fieldClass)
                        .and(ElementMatchers.not(ElementMatchers.isEquals())
                                .and(ElementMatchers.not(ElementMatchers.isHashCode())
                                        .and(ElementMatchers.not(ElementMatchers.isToString())))))
                .intercept(MethodDelegation.to(new GreenMethodInterceptor(containerField)))
                .make()
                .load(fieldClass.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();

        setReflectionValue(field, target, mockObject);
    }
}
