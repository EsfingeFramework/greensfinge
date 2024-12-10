package br.com.ita.greenframework.annotation;

import br.com.ita.greenframework.configuration.metriccalculate.EnergySavingsCalculator;
import br.com.ita.greenframework.configuration.metriccalculate.NoMetricCalculate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EnergySavingCustomCalculation {

    Class<? extends EnergySavingsCalculator> implementation() default NoMetricCalculate.class;

    String[] affectedByConfigurations() default {};
}
