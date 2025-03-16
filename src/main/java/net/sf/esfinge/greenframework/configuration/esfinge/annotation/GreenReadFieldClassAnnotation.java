package net.sf.esfinge.greenframework.configuration.esfinge.annotation;

import net.sf.esfinge.greenframework.configuration.esfinge.processor.GreenReadFieldAnnotationProcessor;
import net.sf.esfinge.metadata.annotation.container.AnnotationReadingConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@AnnotationReadingConfig(GreenReadFieldAnnotationProcessor.class)
public @interface GreenReadFieldClassAnnotation {
}
