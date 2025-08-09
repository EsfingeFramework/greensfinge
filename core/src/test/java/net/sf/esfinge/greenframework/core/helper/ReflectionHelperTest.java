package net.sf.esfinge.greenframework.core.helper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectionHelperTest {

    public static void injectValue(Object target, String fieldName, Object value) {
        try {
            Field field = getFieldFromHierarchy(target.getClass(), fieldName);
            if (field == null) {
                throw new IllegalArgumentException("Campo '" + fieldName + "' não encontrado em " + target.getClass());
            }

            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao injetar valor via reflexão", e);
        }
    }

    private static Field getFieldFromHierarchy(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
}
