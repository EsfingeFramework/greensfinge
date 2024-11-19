package br.com.ita.greenframework.annotations;

import br.com.ita.greenframework.dto.GreenNumberConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@GreenConfigAnnotation(annotationName = GreenNumberConfig.class, className = GreenNumberConfiguration.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GreenNumberConfig {

    GreenDefault configurationKey();
}
