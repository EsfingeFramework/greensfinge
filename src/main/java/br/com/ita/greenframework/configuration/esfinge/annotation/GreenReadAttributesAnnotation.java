package br.com.ita.greenframework.configuration.esfinge.annotation;

import br.com.ita.greenframework.configuration.esfinge.processor.GreenReadAttributesAnnotationProcessor;
import net.sf.esfinge.metadata.annotation.container.AnnotationReadingConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@AnnotationReadingConfig(GreenReadAttributesAnnotationProcessor.class)
public @interface GreenReadAttributesAnnotation {
}
