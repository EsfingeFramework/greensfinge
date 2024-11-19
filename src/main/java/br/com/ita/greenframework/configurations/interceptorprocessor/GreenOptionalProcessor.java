package br.com.ita.greenframework.configurations.interceptorprocessor;

import br.com.ita.greenframework.annotations.GreenOptional;
import br.com.ita.greenframework.configurations.GreenGenericMocker;
import br.com.ita.greenframework.configurations.esfinge.dto.ContainerField;
import br.com.ita.greenframework.dto.GreenOptionalConfiguration;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class GreenOptionalProcessor extends GreenStrategyProcessor {

    @Override
    public void process(Field field, ContainerField containerField, Object target) {
        GreenOptional annotation = field.getAnnotation(GreenOptional.class);
        GreenOptionalConfiguration configuration = getThreadLocalConfiguration(annotation.configurationKey());

        if (configuration.isIgnore()) {
            field.setAccessible(true);

            Class fieldClass = null;
            try {
                fieldClass = Class.forName(field.getGenericType().getTypeName());


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
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
