package br.com.ita.greenframework.mock.service.metrictest;

import br.com.ita.greenframework.annotation.EnergySavingCustomCalculation;
import br.com.ita.greenframework.annotation.GreenAdjustableNumber;
import br.com.ita.greenframework.annotation.GreenConfigKey;
import br.com.ita.greenframework.annotation.GreenSwitch;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MathService {

    @GreenConfigKey("keyNumber1")
    @GreenAdjustableNumber
    private final Integer number1 = 0;

    @GreenConfigKey("keyNumber2")
    @GreenAdjustableNumber
    private final Integer number2 = 0;

    @GreenConfigKey("keyNumber3")
    @GreenAdjustableNumber
    private final Integer number3 = 0;

    @GreenConfigKey("keyNumber4")
    @GreenAdjustableNumber
    private final Long number4 = 0L;

    @GreenConfigKey("keySumService")
    @GreenSwitch
    private SumService sumService = new SumService();

    public Integer calculateSum() {
        return number1 + number2;
    }

    @EnergySavingCustomCalculation(affectedByConfigurations = {"keyNumber3"})
    public Integer countNumber3() {
        log.info("Number3 value: {}", number3);
        return number3;
    }

    public Long countNumber4() {
        log.info("Number3 value: {} | Number4 value: {}", number3, number4);
        return sumService.sum(number3, number4);
    }
}
