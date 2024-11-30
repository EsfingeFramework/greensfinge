package br.com.ita.greenframework.annotation;

import br.com.ita.greenframework.util.GreenConstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GreenDefaultReturn {

    String strValue() default GreenConstant.STR_DEFAULT_VALUE;
    double numberValue() default GreenConstant.DOUBLE_DEFAULT_VALUE;
}
