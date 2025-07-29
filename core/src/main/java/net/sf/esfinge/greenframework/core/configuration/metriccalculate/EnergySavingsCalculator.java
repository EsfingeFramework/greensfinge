package net.sf.esfinge.greenframework.core.configuration.metriccalculate;

import net.sf.esfinge.greenframework.core.entity.GreenMetricCalculate;

public interface EnergySavingsCalculator {

    Double calculateSavedValue(GreenMetricCalculate greenMetricCalculate);
}
