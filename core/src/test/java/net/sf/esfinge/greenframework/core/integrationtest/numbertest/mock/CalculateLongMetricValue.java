package net.sf.esfinge.greenframework.core.integrationtest.numbertest.mock;

import net.sf.esfinge.greenframework.core.configuration.metriccalculate.EnergySavingsCalculator;
import net.sf.esfinge.greenframework.core.entity.GreenMetricCalculate;

public class CalculateLongMetricValue  implements EnergySavingsCalculator {

    @Override
    public Double calculateSavedValue(GreenMetricCalculate greenMetricCalculate) {
        return 2.3;
    }
}
