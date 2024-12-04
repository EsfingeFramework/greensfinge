package br.com.ita.greenframework.configuration.esfinge.processor;

import lombok.SneakyThrows;
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
import java.util.List;
import java.util.Map;

public class GreenReadAttributesAnnotationProcessor extends GreenReadProcessor implements AnnotationReadingProcessor {

    String property;

    @Override
    public void initAnnotation(Annotation an, AnnotatedElement elementWithMetadata) throws AnnotationValidationException {
        property = ((Field) elementWithMetadata).getName();
    }

    @SneakyThrows
    @Override
    public void read(AnnotatedElement elementWithMetadata, Object container, ContainerTarget target) throws AnnotationReadingException {
        Map<String, Object> values = new HashMap<>();

        List<Annotation> annotations = getAllGreenAnnotations(elementWithMetadata);
        for (Annotation annotation : annotations) {
            for (Method method : annotation.annotationType().getMethods()) {
                if (!method.getDeclaringClass().getName().startsWith("java.lang")) {
                    Object value = method.invoke(annotation);
                    if (value instanceof Annotation) {
                        processAnnotation(value, values);
                    } else {
                        values.put(method.getName(), value);
                    }
                }
            }
        }
        BeanUtils.setProperty(container, property, values);
    }

    private void processAnnotation(Object value, Map<String, Object> values) throws InvocationTargetException, IllegalAccessException {
        Annotation nestedAnnotation = (Annotation) value;
        for (Method nestedMethod : nestedAnnotation.annotationType().getDeclaredMethods()) {
            Object nestedValue = nestedMethod.invoke(nestedAnnotation);
            values.put(nestedMethod.getName(), nestedValue);
        }
    }
}
