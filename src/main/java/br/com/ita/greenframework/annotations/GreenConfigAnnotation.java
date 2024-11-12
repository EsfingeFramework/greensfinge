package br.com.ita.greenframework.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GreenConfigAnnotation {

    Class annotationName();
    Class className();

}
