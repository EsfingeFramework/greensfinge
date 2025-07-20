package net.sf.esfinge.greenframework.core.annotation;

import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.metadata.annotation.validator.NeedsToHave;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@GreenConfigAnnotation(annotationName = GreenSwitch.class, className = GreenSwitchConfiguration.class)
@NeedsToHave(GreenConfigKey.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GreenSwitch {

}
