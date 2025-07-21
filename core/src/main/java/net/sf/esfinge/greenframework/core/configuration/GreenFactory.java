package net.sf.esfinge.greenframework.core.configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ClassContainer;
import net.sf.esfinge.greenframework.core.util.GreenReflectionUtil;
import net.sf.esfinge.metadata.AnnotationReader;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Field;

import static net.sf.esfinge.greenframework.core.util.GreenConstant.FIELD_ORIGINAL_BEAN;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GreenFactory {

    private static final ByteBuddy BYTE_BUDDY = new ByteBuddy();

    @SneakyThrows
    public static <T> T greenify(Class<T> target) {

        AnnotationReader reader = new AnnotationReader();
        ClassContainer classContainer = reader.readingAnnotationsTo(target, ClassContainer.class);

        return BYTE_BUDDY.subclass(target)
                .method(ElementMatchers.isDeclaredBy(target)
                        .and(ElementMatchers.not(ElementMatchers.isEquals())
                        .and(ElementMatchers.not(ElementMatchers.isHashCode())
                        .and(ElementMatchers.not(ElementMatchers.isToString())))))
                .intercept(MethodDelegation.to(new GreenClassMethodProxyInterceptor(classContainer)))
                .make()
                .load(target.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();
    }

    @SneakyThrows
    public static Object greenify(Object original) {
        Class<?> targetClass = original.getClass();

        AnnotationReader reader = new AnnotationReader();
        ClassContainer classContainer = reader.readingAnnotationsTo(targetClass, ClassContainer.class);

        Class<?> proxyClass = new ByteBuddy()
                .subclass(targetClass)
                .defineField(FIELD_ORIGINAL_BEAN, targetClass, Visibility.PUBLIC)
                .method(ElementMatchers.isDeclaredBy(targetClass)
                        .and(ElementMatchers.not(ElementMatchers.isEquals()))
                        .and(ElementMatchers.not(ElementMatchers.isHashCode()))
                        .and(ElementMatchers.not(ElementMatchers.isToString())))
                .intercept(MethodDelegation.to(new GreenClassMethodProxyInterceptor(classContainer)))
                .make()
                .load(targetClass.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                .getLoaded();

        Object proxy = new ObjenesisStd().newInstance(proxyClass);

        Field originalBeanField = proxyClass.getDeclaredField(FIELD_ORIGINAL_BEAN);
        GreenReflectionUtil.injectValue(originalBeanField, proxy, original);

        return proxy;
    }

}
