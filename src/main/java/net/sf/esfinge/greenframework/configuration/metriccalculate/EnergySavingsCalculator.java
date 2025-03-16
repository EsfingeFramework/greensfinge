package net.sf.esfinge.greenframework.configuration.metriccalculate;

import net.sf.esfinge.greenframework.dto.project.GreenMetricCalculate;

public interface EnergySavingsCalculator {

    Double calculateSavedValue(GreenMetricCalculate greenMetricCalculate);
}
