package br.com.ita.greenframework.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EnergySavingFixedEstimation {

    double energySavedValue() default 0.0;
}