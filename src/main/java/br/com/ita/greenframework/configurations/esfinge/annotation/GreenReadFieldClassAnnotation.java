package br.com.ita.greenframework.configurations.esfinge.annotation;

import br.com.ita.greenframework.configurations.esfinge.processor.GreenReadFieldAnnotationProcessor;
import net.sf.esfinge.metadata.annotation.container.AnnotationReadingConfig;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@AnnotationReadingConfig(GreenReadFieldAnnotationProcessor.class)
public @interface GreenReadFieldClassAnnotation {
}
