package br.com.ita.greenframework.configuration.metriccalculate;

import br.com.ita.greenframework.dto.project.GreenMetricCalculate;

public interface EnergySavingsCalculator {

    Double calculateSavedValue(GreenMetricCalculate greenMetricCalculate);
}
