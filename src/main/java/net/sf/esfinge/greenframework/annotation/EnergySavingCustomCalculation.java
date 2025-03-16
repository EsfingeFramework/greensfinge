package net.sf.esfinge.greenframework.annotation;

import net.sf.esfinge.greenframework.configuration.metriccalculate.EnergySavingsCalculator;
import net.sf.esfinge.greenframework.configuration.metriccalculate.NoMetricCalculate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EnergySavingCustomCalculation {

    Class<? extends EnergySavingsCalculator> implementation() default NoMetricCalculate.class;

    String[] affectedByConfigurations() default {};
}
