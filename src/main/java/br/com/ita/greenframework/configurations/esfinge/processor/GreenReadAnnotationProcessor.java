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
import java.util.Objects;

public class GreenReadAnnotationProcessor extends GreenReadProcessor implements AnnotationReadingProcessor {

    String property;

    @Override
    public void initAnnotation(Annotation an, AnnotatedElement elementWithMetadata) throws AnnotationValidationException {
        property = ((Field) elementWithMetadata).getName();
    }

    @Override
    public void read(AnnotatedElement elementWithMetadata, Object container, ContainerTarget target) throws AnnotationReadingException {
        Annotation annotation = getGreenAnnotation(elementWithMetadata);
        try {
            BeanUtils.setProperty(container, property, Objects.nonNull(annotation));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
