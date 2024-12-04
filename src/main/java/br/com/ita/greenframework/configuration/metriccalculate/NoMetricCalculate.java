package br.com.ita.greenframework.configuration.metriccalculate;

import br.com.ita.greenframework.dto.project.GreenMetricCalculate;

public class NoMetricCalculate implements EnergySavingsCalculator {

    @Override
    public Double calculateSavedValue(GreenMetricCalculate greenMetricCalculate) {
        return null;
    }
}
