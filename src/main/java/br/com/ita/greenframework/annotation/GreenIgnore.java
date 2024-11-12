package br.com.ita.greenframework.annotation;

import br.com.ita.greenframework.util.Constants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GreenIgnore {

    boolean ignore() default false;

    String strValue() default Constants.GREEN_IGNORE_DEFAULT_STR_VALUE;

    int numericValue() default Constants.GREEN_IGNORE_DEFAULT_INT_VALUE;
}
