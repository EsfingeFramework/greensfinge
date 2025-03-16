package net.sf.esfinge.greenframework.configuration.metriccalculate;

import net.sf.esfinge.greenframework.dto.project.GreenMetricCalculate;

public class NoMetricCalculate implements EnergySavingsCalculator {

    @Override
    public Double calculateSavedValue(GreenMetricCalculate greenMetricCalculate) {
        return null;
    }
}
