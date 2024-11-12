package br.com.ita.greenframework.proxyprocessor;

import br.com.ita.greenframework.annotation.GreenIgnore;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyProcessor {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final T target) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Method actualMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());

                        if (actualMethod.isAnnotationPresent(GreenIgnore.class)) {
                            System.out.println("Ignored method: " + method.getName());
                            return null; // Ignora a execução do método
                        }
                        return method.invoke(target, args);
                    }
                }
        );
    }
}
