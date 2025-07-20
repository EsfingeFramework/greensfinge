package net.sf.esfinge.greenframework.core.configuration.metriccalculate;

import net.sf.esfinge.greenframework.core.dto.project.GreenMetricCalculate;

public interface EnergySavingsCalculator {

    Double calculateSavedValue(GreenMetricCalculate greenMetricCalculate);
}
