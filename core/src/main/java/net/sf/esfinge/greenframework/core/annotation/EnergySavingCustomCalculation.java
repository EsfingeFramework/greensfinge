package net.sf.esfinge.greenframework.core.annotation;

import net.sf.esfinge.greenframework.core.configuration.metriccalculate.EnergySavingsCalculator;
import net.sf.esfinge.greenframework.core.configuration.metriccalculate.NoMetricCalculate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EnergySavingCustomCalculation {

    Class<? extends EnergySavingsCalculator> implementation() default NoMetricCalculate.class;

    String[] affectedByConfigurations() default {};
}
