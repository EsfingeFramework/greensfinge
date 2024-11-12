package br.com.ita.greenframework.configuration;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.reflect.Method;

public class GreenGenericMocker {

    public static String mockdoSomething2(@Origin Method method, @AllArguments Object[] args) {
        String methodName = method.getName();
        System.out.println("Intercepted method: " + methodName);

        if (methodName.startsWith("doSomething")) {
            return "Mocked response for " + methodName;
        }
        return null;
    }
}
