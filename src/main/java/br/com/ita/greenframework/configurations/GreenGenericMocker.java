package br.com.ita.greenframework.configurations;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;

public class GreenGenericMocker {

    @RuntimeType
    public static Object mockAnyMethod(@Origin Method method, @AllArguments Object[] args) {
        String methodName = method.getName();
        System.out.println("Intercepted method: " + methodName);

        if (method.getReturnType().equals(String.class)) {
            return "Mocked String Result";
        } else if (method.getReturnType().equals(Integer.class)) {
            return 42;
        } else if (method.getReturnType().equals(Void.TYPE)) {
            return null;
        }
        return null;
    }
}
