package net.sf.esfinge.greenframework.core.configuration.metriccalculate;

import net.sf.esfinge.greenframework.core.entity.GreenMetricCalculate;

public class NoMetricCalculate implements EnergySavingsCalculator {

    @Override
    public Double calculateSavedValue(GreenMetricCalculate greenMetricCalculate) {
        return null;
    }
}
