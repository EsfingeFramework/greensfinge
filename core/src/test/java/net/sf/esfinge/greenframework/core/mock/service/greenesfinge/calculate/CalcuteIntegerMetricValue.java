package net.sf.esfinge.greenframework.core.mock.service.greenesfinge.calculate;

import net.sf.esfinge.greenframework.core.configuration.metriccalculate.EnergySavingsCalculator;
import net.sf.esfinge.greenframework.core.dto.project.GreenMetricCalculate;

public class CalcuteIntegerMetricValue implements EnergySavingsCalculator {

    @Override
    public Double calculateSavedValue(GreenMetricCalculate greenMetricCalculate) {
        return 1.0;
    }
}
