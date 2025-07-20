package net.sf.esfinge.greenframework.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;

import static net.sf.esfinge.greenframework.core.util.GreenConstant.FIELD_ORIGINAL_BEAN;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GreenReflectionUtil {

    @SneakyThrows
    public static void injectValue(Field field, Object originalObject, Object valueObject) {
        field.setAccessible(true);

        field.set(originalObject, valueObject);
    }

    @SneakyThrows
    public static Object getFieldValue(Field field, Object originalObject) {
        field.setAccessible(true);

        return field.get(originalObject);
    }

    @SneakyThrows
    public static void injectOriginalBeanIntoProxy(Object proxyInstance, Field proxyField, Object parentOriginalBean) {
        Field originalBeanField = proxyInstance.getClass().getDeclaredField(FIELD_ORIGINAL_BEAN);

        Class<?> fieldType = proxyField.getType();

        Field fieldInParent = findFieldByType(parentOriginalBean.getClass(), fieldType);
        if (fieldInParent == null) {
            throw new IllegalStateException("Nenhum campo do tipo " + fieldType + " encontrado em " + parentOriginalBean.getClass().getName());
        }

        Object springBeanInstance = getFieldValue(fieldInParent, parentOriginalBean);

        injectValue(originalBeanField, proxyInstance, springBeanInstance);
    }

    private static Field findFieldByType(Class<?> clazz, Class<?> targetType) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.getType().equals(targetType))
                .findFirst()
                .map(field -> {
                    field.setAccessible(true);
                    return field;
                })
                .orElse(null);
    }

    public static Object resolveInjectionTarget(Object target) {
        return isProxy(target) ? proxyFrom(target): target;
    }

    @SneakyThrows
    private static Object proxyFrom(Object target) {
        Field field = target.getClass().getDeclaredField(FIELD_ORIGINAL_BEAN);
        return getFieldValue(field, target);
    }

    private static boolean isProxy(Object obj) {
        if (obj == null) return false;
        String className = obj.getClass().getName();
        return className.contains("ByteBuddy") || className.contains("$$");
    }
}
