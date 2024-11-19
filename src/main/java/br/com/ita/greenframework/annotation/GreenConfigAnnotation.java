package br.com.ita.greenframework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GreenConfigAnnotation {

    Class annotationName();
    Class className();

}
