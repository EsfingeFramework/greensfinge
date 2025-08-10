package net.sf.esfinge.greenframework.core.configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ClassContainer;
import net.sf.esfinge.greenframework.core.util.GreenReflectionUtil;
import net.sf.esfinge.metadata.AnnotationReader;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import static net.sf.esfinge.greenframework.core.util.GreenConstant.FIELD_ORIGINAL_BEAN;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class GreenFactory {

    private static final ByteBuddy BYTE_BUDDY = new ByteBuddy();

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static <T> T greenify(Class<T> target) {
        Constructor<T> constructor = target.getDeclaredConstructor();
        T instance = constructor.newInstance();

        Object greenifyInstance = greenify(instance);

        for (Field field : target.getDeclaredFields()) {
            GreenReflectionUtil.getFieldValue(field, instance);

            Class<?> fieldType = field.getType();
            try {
                Object fieldInstance = fieldType.getDeclaredConstructor().newInstance();
                GreenReflectionUtil.injectValue(field, instance, greenify(fieldInstance));
            }catch (NoSuchMethodException e) {
                //Nothing to do
            }

        }

        return (T) greenifyInstance;
    }

    @SneakyThrows
    public static Object greenify(Object original) {
        if (Objects.nonNull(original) && !hasGreenAnnotations(original.getClass())){
            return original;
        }

        Class<?> targetClass = original.getClass();

        AnnotationReader reader = new AnnotationReader();
        ClassContainer classContainer = reader.readingAnnotationsTo(targetClass, ClassContainer.class);

        Class<?> proxyClass = BYTE_BUDDY
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

    private static boolean hasGreenAnnotations(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .anyMatch(f -> f.isAnnotationPresent(GreenConfigKey.class)) ||
                Arrays.stream(clazz.getDeclaredMethods())
                        .anyMatch(m -> m.isAnnotationPresent(GreenConfigKey.class));
    }

}
