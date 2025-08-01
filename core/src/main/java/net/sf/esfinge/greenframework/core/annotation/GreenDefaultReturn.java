package net.sf.esfinge.greenframework.core.annotation;

import net.sf.esfinge.greenframework.core.util.GreenConstant;
import net.sf.esfinge.metadata.annotation.validator.NeedsToHave;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@NeedsToHave(GreenConfigKey.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface GreenDefaultReturn {

    String strValue() default GreenConstant.STR_DEFAULT_VALUE;
    double numberValue() default GreenConstant.DOUBLE_DEFAULT_VALUE;
}
