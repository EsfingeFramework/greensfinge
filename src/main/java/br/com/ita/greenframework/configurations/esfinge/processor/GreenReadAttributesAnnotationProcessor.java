package br.com.ita.greenframework.configurations.esfinge.processor;

import net.sf.esfinge.metadata.AnnotationReadingException;
import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.container.AnnotationReadingProcessor;
import net.sf.esfinge.metadata.container.ContainerTarget;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GreenReadAttributesAnnotationProcessor extends GreenReadProcessor implements AnnotationReadingProcessor {

    String property;

    @Override
    public void initAnnotation(Annotation an, AnnotatedElement elementWithMetadata) throws AnnotationValidationException {
        property = ((Field) elementWithMetadata).getName();
    }

    @Override
    public void read(AnnotatedElement elementWithMetadata, Object container, ContainerTarget target) throws AnnotationReadingException {
        Map<String, Object> values = new HashMap<>();

        Annotation annotation = getGreenAnnotation(elementWithMetadata);
        if(Objects.nonNull(annotation)) {
            try {
                for (Method method : annotation.annotationType().getMethods()) {
                    if(!method.getDeclaringClass().getName().startsWith("java.lang")) {
                        Object value = method.invoke(annotation);
                        if (value instanceof Annotation) {
                            Annotation nestedAnnotation = (Annotation) value;
                            for (Method nestedMethod : nestedAnnotation.annotationType().getDeclaredMethods()) {
                                Object nestedValue = nestedMethod.invoke(nestedAnnotation);
                                values.put(nestedMethod.getName(), nestedValue);
                            }
                        } else {
                            values.put(method.getName(), value);
                        }

                    }
                }

                BeanUtils.setProperty(container, property, values);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
