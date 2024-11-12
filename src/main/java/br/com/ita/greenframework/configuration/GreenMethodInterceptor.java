package br.com.ita.greenframework.configuration;

import br.com.ita.greenframework.annotations.GreenOptional;
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
        // Lógica antes da execução do método original
        System.out.println("Before method execution: " + method.toGenericString());

        Field[] declaredFields = target.getClass().getSuperclass().getDeclaredFields();
        for (Field field : declaredFields) {
            if(field.isAnnotationPresent(GreenOptional.class)) {
                field.setAccessible(true);

                Class fieldClass = Class.forName(field.getGenericType().getTypeName());

                Object mockObject = new ByteBuddy()
                        .subclass(fieldClass)
                        .method(ElementMatchers.isDeclaredBy(fieldClass)
                                .and(ElementMatchers.not(ElementMatchers.isEquals())
                                .and(ElementMatchers.not(ElementMatchers.isHashCode())
                                .and(ElementMatchers.not(ElementMatchers.isToString()))))) // Exclui os métodos equals, hashCode e toString
                        .intercept(MethodDelegation.to(GreenGenericMocker.class)) // Usa o método genérico de mock
                        .make()
                        .load(fieldClass.getClassLoader())
                        .getLoaded()
                        .getDeclaredConstructor()
                        .newInstance();

                field.set(target, mockObject);
            }
        }

        // Chamando o método original usando o proceed (zuper.call())
        Object result = zuper.call();

        // Lógica após a execução do método original
        System.out.println("After method execution: " + method.toGenericString());

        return result;
    }
}
