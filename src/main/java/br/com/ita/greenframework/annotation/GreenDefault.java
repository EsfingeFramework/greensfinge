package br.com.ita.greenframework.annotation;

import br.com.ita.greenframework.dto.annotation.GreenOptionalConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@GreenConfigAnnotation(annotationName = GreenOptional.class, className = GreenOptionalConfiguration.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GreenDefault {

    String configurationKey();
}
