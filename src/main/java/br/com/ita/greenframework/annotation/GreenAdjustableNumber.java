package br.com.ita.greenframework.annotation;

import br.com.ita.greenframework.dto.annotation.GreenAdjustableNumberConfiguration;
import net.sf.esfinge.metadata.annotation.validator.NeedsToHave;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@GreenConfigAnnotation(annotationName = GreenAdjustableNumber.class, className = GreenAdjustableNumberConfiguration.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@NeedsToHave(GreenConfigKey.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface GreenAdjustableNumber {

}