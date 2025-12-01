package net.sf.esfinge.greenframework.core.configuration.esfinge.processor;

import lombok.SneakyThrows;
import net.sf.esfinge.metadata.AnnotationReadingException;
import net.sf.esfinge.metadata.AnnotationValidationException;
import net.sf.esfinge.metadata.container.AnnotationReadingProcessor;
import net.sf.esfinge.metadata.container.ContainerTarget;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class GreenReadMethodNameAnnotationProcessor implements AnnotationReadingProcessor {

    String property;

    @Override
    public void initAnnotation(Annotation an, AnnotatedElement elementWithMetadata) throws AnnotationValidationException {
        property = ((Field) elementWithMetadata).getName();
    }

    @SneakyThrows
    @Override
    public void read(AnnotatedElement elementWithMetadata, Object container, ContainerTarget target) throws AnnotationReadingException {
        BeanUtils.setProperty(container, property, ((Method) elementWithMetadata).getName());
    }
}
