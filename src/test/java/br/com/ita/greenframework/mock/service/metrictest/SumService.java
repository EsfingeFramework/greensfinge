package br.com.ita.greenframework.mock.service.metrictest;

import br.com.ita.greenframework.annotation.GreenDefaultReturn;
import br.com.ita.greenframework.annotation.GreenMetric;
import br.com.ita.greenframework.mock.service.greenesfinge.calculate.CalculateLongMetricValue;

public class SumService {

    @GreenDefaultReturn(numberValue = 8)
    @GreenMetric(affectedByConfigurations = {"keyNumber3", "keyNumber4"}, classMetricValue = CalculateLongMetricValue.class)
    public Long sum(Integer number3, Long number4) {
        return number3 + number4;
    }
}
