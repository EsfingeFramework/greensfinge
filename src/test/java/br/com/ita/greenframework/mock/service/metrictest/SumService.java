package br.com.ita.greenframework.mock.service.metrictest;

import br.com.ita.greenframework.annotation.GreenReturnWhenSwitchOff;
import br.com.ita.greenframework.annotation.EnergySavingCustomCalculation;
import br.com.ita.greenframework.mock.service.greenesfinge.calculate.CalculateLongMetricValue;

public class SumService {

    @GreenReturnWhenSwitchOff(numberValue = 8)
    @EnergySavingCustomCalculation(energySavedValue = 3.2, affectedByConfigurations = {"keyNumber3", "keyNumber4"}, implementation = CalculateLongMetricValue.class)
    public Long sum(Integer number3, Long number4) {
        return number3 + number4;
    }
}
