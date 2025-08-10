package net.sf.esfinge.greenframework.core.integrationtest.metrictest.mock;

import net.sf.esfinge.greenframework.core.annotation.*;
import net.sf.esfinge.greenframework.core.integrationtest.numbertest.mock.CalculateLongMetricValue;

public class SumService {

    @GreenConfigKey("SumService#sum")
    @GreenSwitchOff
    @EnergySavingCustomCalculation(affectedByConfigurations = {"keyNumber3", "keyNumber4"}, implementation = CalculateLongMetricValue.class)
    public Long sum(Integer number3, Long number4) {
        return number3 + number4;
    }

    @GreenConfigKey("SumService#minus")
    @GreenSwitchOff
    @GreenDefaultReturn(numberValue = 8)
    @EnergySavingFixedEstimation(energySavedValue = 4.9)
    public Integer minus(Integer number3, Integer number4) {
        return number3 - number4;
    }
}
