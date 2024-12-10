package br.com.ita.greenframework.integrationtest;

import br.com.ita.greenframework.configuration.GreenFactory;
import br.com.ita.greenframework.configuration.facade.GreenConfigurationFacade;
import br.com.ita.greenframework.configuration.facade.GreenMetricFacade;
import br.com.ita.greenframework.dto.annotation.GreenSwitchConfiguration;
import br.com.ita.greenframework.dto.project.GreenMetric;
import br.com.ita.greenframework.mock.service.metrictest.MathService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IntegrationMetricTest {

    private final MathService mathService = GreenFactory.greenify(MathService.class);

    @Test
    @Order(1)
    void testShouldMetricCustomEnergyWithSeveralCall() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        GreenMetricFacade metricFacade = new GreenMetricFacade();

        Double mockMethodReturn = 45D;

        int countCallMethod = 15;

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keySumService")
                .numberDefaultValue(mockMethodReturn)
                .build());

        for (int i = 0; i < countCallMethod; i++) {
            assertEquals(mockMethodReturn.intValue(), mathService.countNumber4());
        }

        GreenMetric greenMetric = metricFacade.getSavedEnergy().stream().filter(
                        saved -> "br.com.ita.greenframework.mock.service.metrictest.SumService#sum".equals(saved.getMethod())
                ).findFirst()
                .orElse(null);
        assertNotNull(greenMetric);
        assertEquals(countCallMethod , greenMetric.getCountCalled());
        //Value from CalculateLongMetricValue
        assertEquals(countCallMethod * 2.3 , greenMetric.getSavedValue());
        assertEquals(2.3 , greenMetric.getMetricSavedValue());
    }

    @Test
    @Order(1)
    void testShouldMetricFixedEnergyWithSeveralCall() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        GreenMetricFacade metricFacade = new GreenMetricFacade();

        int countCallMethod = 15;

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keySumService")
                .build());

        for (int i = 0; i < countCallMethod; i++) {
            assertEquals(8, mathService.minusOperation());
        }

        GreenMetric greenMetric = metricFacade.getSavedEnergy().stream().filter(
                        saved -> "br.com.ita.greenframework.mock.service.metrictest.SumService#minus".equals(saved.getMethod())
                ).findFirst()
                .orElse(null);
        assertNotNull(greenMetric);
        assertEquals(countCallMethod , greenMetric.getCountCalled());
        //Value from CalculateLongMetricValue
        assertEquals(countCallMethod * 4.9 , greenMetric.getSavedValue());
        assertEquals(4.9 , greenMetric.getMetricSavedValue());
    }
}
