package br.com.ita.greenframework.annotation;

import br.com.ita.greenframework.dto.GreenOptionalConfiguration;
import br.com.ita.greenframework.util.GreenConstant;
import net.sf.esfinge.metadata.annotation.finder.SearchInsideAnnotations;
import net.sf.esfinge.metadata.annotation.finder.SearchOnEnclosingElements;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@GreenConfigAnnotation(annotationName = GreenOptional.class, className = GreenOptionalConfiguration.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@SearchOnEnclosingElements
@SearchInsideAnnotations
public @interface GreenOptional {

    GreenDefault configurationKey();

    String strDefaultValue() default GreenConstant.STR_DEFAULT_VALUE;
    int numberDefaultValue() default GreenConstant.INT_DEFAULT_VALUE;

}
