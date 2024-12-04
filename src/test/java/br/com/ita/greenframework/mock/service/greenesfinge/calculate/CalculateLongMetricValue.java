package br.com.ita.greenframework.mock.service.greenesfinge.calculate;

import br.com.ita.greenframework.configuration.metriccalculate.EnergySavingsCalculator;
import br.com.ita.greenframework.dto.project.GreenMetricCalculate;

public class CalculateLongMetricValue  implements EnergySavingsCalculator {

    @Override
    public Double calculateSavedValue(GreenMetricCalculate greenMetricCalculate) {
        return 1.0;
    }
}
