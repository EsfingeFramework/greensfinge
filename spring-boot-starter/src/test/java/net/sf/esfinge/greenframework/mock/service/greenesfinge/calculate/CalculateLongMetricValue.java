package net.sf.esfinge.greenframework.mock.service.greenesfinge.calculate;

import net.sf.esfinge.greenframework.configuration.metriccalculate.EnergySavingsCalculator;
import net.sf.esfinge.greenframework.dto.project.GreenMetricCalculate;

public class CalculateLongMetricValue  implements EnergySavingsCalculator {

    @Override
    public Double calculateSavedValue(GreenMetricCalculate greenMetricCalculate) {
        return 2.3;
    }
}
