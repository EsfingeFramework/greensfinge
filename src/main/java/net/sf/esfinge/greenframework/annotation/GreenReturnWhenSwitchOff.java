package net.sf.esfinge.greenframework.annotation;

import net.sf.esfinge.greenframework.util.GreenConstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GreenReturnWhenSwitchOff {

    String strValue() default GreenConstant.STR_DEFAULT_VALUE;
    double numberValue() default GreenConstant.DOUBLE_DEFAULT_VALUE;
}
