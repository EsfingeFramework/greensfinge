package br.com.ita.greenframework.configurations;

import br.com.ita.greenframework.annotations.GreenOptional;
import br.com.ita.greenframework.dto.OptionalConfiguration;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class GreenMethodInterceptor {

    @RuntimeType
    public Object intercept(@This Object target, @AllArguments Object[] args, @Origin Method method, @SuperCall Callable<?> zuper) throws Exception {
        System.out.println("Before method execution: " + method.toGenericString());

        Field[] declaredFields = target.getClass().getSuperclass().getDeclaredFields();
        for (Field field : declaredFields) {
            if(field.isAnnotationPresent(GreenOptional.class)) {
                GreenOptional annotation = field.getAnnotation(GreenOptional.class);
                GreenThreadLocal greenThreadLocal = new GreenThreadLocal();
                OptionalConfiguration configuration = (OptionalConfiguration)greenThreadLocal.getValue(annotation.configurationKey());

                if(configuration.isIgnore()) {
                    field.setAccessible(true);

                    Class fieldClass = Class.forName(field.getGenericType().getTypeName());

                    Object mockObject = new ByteBuddy()
                            .subclass(fieldClass)
                            .method(ElementMatchers.isDeclaredBy(fieldClass)
                                    .and(ElementMatchers.not(ElementMatchers.isEquals())
                                    .and(ElementMatchers.not(ElementMatchers.isHashCode())
                                    .and(ElementMatchers.not(ElementMatchers.isToString())))))
                            .intercept(MethodDelegation.to(GreenGenericMocker.class))
                            .make()
                            .load(fieldClass.getClassLoader())
                            .getLoaded()
                            .getDeclaredConstructor()
                            .newInstance();

                    field.set(target, mockObject);
                }
            }
        }

        Object result = zuper.call();

        System.out.println("After method execution: " + method.toGenericString());

        return result;
    }
}
