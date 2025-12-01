package net.sf.esfinge.greenframework.core.configuration.esfinge.annotation;

import net.sf.esfinge.greenframework.core.configuration.esfinge.processor.GreenReadFieldTypeAnnotationProcessor;
import net.sf.esfinge.metadata.annotation.container.AnnotationReadingConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@AnnotationReadingConfig(GreenReadFieldTypeAnnotationProcessor.class)
public @interface GreenReadFieldTypeAnnotation {
}
