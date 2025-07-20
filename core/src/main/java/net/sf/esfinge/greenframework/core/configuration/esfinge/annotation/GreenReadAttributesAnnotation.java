package net.sf.esfinge.greenframework.core.configuration.esfinge.annotation;

import net.sf.esfinge.greenframework.core.configuration.esfinge.processor.GreenReadAttributesAnnotationProcessor;
import net.sf.esfinge.metadata.annotation.container.AnnotationReadingConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@AnnotationReadingConfig(GreenReadAttributesAnnotationProcessor.class)
public @interface GreenReadAttributesAnnotation {
}
