package br.com.ita.greenframework.configuration;

import br.com.ita.greenframework.configuration.esfinge.dto.ClassContainer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.sf.esfinge.metadata.AnnotationReader;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GreenFactory {

    private static final ByteBuddy BYTE_BUDDY = new ByteBuddy();

    @SuppressWarnings("unchecked")
    public static <T> T greenify(Class<T> target) throws Exception {

        AnnotationReader reader = new AnnotationReader();
        ClassContainer classContainer = reader.readingAnnotationsTo(target, ClassContainer.class);

            return (T) BYTE_BUDDY.subclass(target)
                    .method(ElementMatchers.isDeclaredBy(target)
                        .and(ElementMatchers.not(ElementMatchers.isEquals())
                        .and(ElementMatchers.not(ElementMatchers.isHashCode())
                        .and(ElementMatchers.not(ElementMatchers.isToString())))))
                    .intercept(MethodDelegation.to(new GreenMethodInterceptor(classContainer)))
                    .make()
                    .load(target.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getLoaded()
                    .newInstance();
    }

}
