package net.sf.esfinge.greenframework.mock.service.metrictest;

import net.sf.esfinge.greenframework.annotation.EnergySavingFixedEstimation;
import net.sf.esfinge.greenframework.annotation.GreenReturnWhenSwitchOff;
import net.sf.esfinge.greenframework.annotation.EnergySavingCustomCalculation;
import net.sf.esfinge.greenframework.mock.service.greenesfinge.calculate.CalculateLongMetricValue;

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
