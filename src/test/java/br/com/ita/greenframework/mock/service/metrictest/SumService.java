package br.com.ita.greenframework.mock.service.metrictest;

import br.com.ita.greenframework.annotation.EnergySavingFixedEstimation;
import br.com.ita.greenframework.annotation.GreenReturnWhenSwitchOff;
import br.com.ita.greenframework.annotation.EnergySavingCustomCalculation;
import br.com.ita.greenframework.mock.service.greenesfinge.calculate.CalculateLongMetricValue;

public class SumService {

    @EnergySavingCustomCalculation(affectedByConfigurations = {"keyNumber3", "keyNumber4"}, implementation = CalculateLongMetricValue.class)
    public Long sum(Integer number3, Long number4) {
        return number3 + number4;
    }

    @GreenReturnWhenSwitchOff(numberValue = 8)
    @EnergySavingFixedEstimation(energySavedValue = 4.9)
    public Integer minus(Integer number3, Integer number4) {
        return number3 - number4;
    }
}
