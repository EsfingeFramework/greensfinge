package net.sf.esfinge.greenframework.core.mock.service.metrictest;

import net.sf.esfinge.greenframework.core.annotation.EnergySavingFixedEstimation;
import net.sf.esfinge.greenframework.core.annotation.GreenDefaultReturn;
import net.sf.esfinge.greenframework.core.annotation.EnergySavingCustomCalculation;
import net.sf.esfinge.greenframework.core.mock.service.greenesfinge.calculate.CalculateLongMetricValue;

public class SumService {

    @EnergySavingCustomCalculation(affectedByConfigurations = {"keyNumber3", "keyNumber4"}, implementation = CalculateLongMetricValue.class)
    public Long sum(Integer number3, Long number4) {
        return number3 + number4;
    }

    @GreenDefaultReturn(numberValue = 8)
    @EnergySavingFixedEstimation(energySavedValue = 4.9)
    public Integer minus(Integer number3, Integer number4) {
        return number3 - number4;
    }
}
