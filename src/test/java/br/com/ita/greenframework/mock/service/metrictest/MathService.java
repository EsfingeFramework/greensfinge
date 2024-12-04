package br.com.ita.greenframework.mock.service.metrictest;

import br.com.ita.greenframework.annotation.GreenDefault;
import br.com.ita.greenframework.annotation.GreenMetric;
import br.com.ita.greenframework.annotation.GreenNumber;
import br.com.ita.greenframework.annotation.GreenOptional;
import br.com.ita.greenframework.mock.service.greenesfinge.calculate.CalculateLongMetricValue;
import br.com.ita.greenframework.mock.service.greenesfinge.calculate.CalcuteIntegerMetricValue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MathService {

    @GreenNumber(value = @GreenDefault(configurationKey = "keyNumber1"))
    private final Integer number1 = 0;

    @GreenNumber(value = @GreenDefault(configurationKey = "keyNumber2"))
    private final Integer number2 = 0;

    @GreenNumber(value = @GreenDefault(configurationKey = "keyNumber3"))
    private final Integer number3 = 0;

    @GreenNumber(value = @GreenDefault(configurationKey = "keyNumber4"))
    private final Long number4 = 0L;

    @GreenOptional(value = @GreenDefault(configurationKey = "keySumService"))
    private SumService sumService = new SumService();

    public Integer calculateSum() {
        return number1 + number2;
    }

    @GreenMetric(affectedByConfigurations = {"keyNumber3"})
    public Integer countNumber3() {
        log.info("Number3 value: {}", number3);
        return number3;
    }

    public Long countNumber4() {
        log.info("Number3 value: {} | Number4 value: {}", number3, number4);
        return sumService.sum(number3, number4);
    }
}
