package br.com.ita.greenframework.configurations;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class GreenFactory {

    private static final ByteBuddy BYTE_BUDDY = new ByteBuddy();

    public static <T> T greenify(Class<T> target) {
        try {
            return (T) BYTE_BUDDY.subclass(target)
                    .method(ElementMatchers.isDeclaredBy(target)
                        .and(ElementMatchers.not(ElementMatchers.isEquals())
                        .and(ElementMatchers.not(ElementMatchers.isHashCode())
                        .and(ElementMatchers.not(ElementMatchers.isToString())))))
                    .intercept(MethodDelegation.to(new GreenMethodInterceptor()))
                    .make()
                    .load(target.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getLoaded()
                    .newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
