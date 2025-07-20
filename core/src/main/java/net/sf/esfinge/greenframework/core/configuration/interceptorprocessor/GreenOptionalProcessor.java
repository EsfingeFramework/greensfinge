package net.sf.esfinge.greenframework.core.configuration.interceptorprocessor;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.sf.esfinge.greenframework.core.configuration.GreenMethodInterceptor;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.core.util.GreenReflectionUtil;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Field;

import static net.sf.esfinge.greenframework.core.util.GreenConstant.FIELD_ORIGINAL_BEAN;

public class GreenOptionalProcessor extends GreenStrategyProcessor {

    @SneakyThrows
    @Override
    public void process(Field field, ContainerField containerField, Object target) {
        Class<?> fieldClass = Class.forName(field.getGenericType().getTypeName());

        Class<?> proxyClass = new ByteBuddy()
                .subclass(fieldClass)
                .defineField(FIELD_ORIGINAL_BEAN, fieldClass, Visibility.PUBLIC)
                .method(ElementMatchers.isDeclaredBy(fieldClass)
                        .and(ElementMatchers.not(ElementMatchers.isEquals()))
                        .and(ElementMatchers.not(ElementMatchers.isHashCode()))
                        .and(ElementMatchers.not(ElementMatchers.isToString())))
                .intercept(MethodDelegation.to(new GreenMethodInterceptor(containerField)))
                .make()
                .load(fieldClass.getClassLoader())
                .getLoaded();

        Object proxyInstance = new ObjenesisStd().newInstance(proxyClass);

        Object parentOriginalBean = target.getClass().getDeclaredField(FIELD_ORIGINAL_BEAN).get(target);

        // Preenche o campo originalBean dentro do proxy, com o bean real gerenciado pelo Spring
        GreenReflectionUtil.injectOriginalBeanIntoProxy(proxyInstance, field, parentOriginalBean);

        // Substitui o campo original pelo proxy no bean pai (HelloController)
        GreenReflectionUtil.injectValue(field, target, proxyInstance);
    }

}
