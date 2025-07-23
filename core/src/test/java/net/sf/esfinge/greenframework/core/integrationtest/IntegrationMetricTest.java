package net.sf.esfinge.greenframework.core.integrationtest;

import net.sf.esfinge.greenframework.core.configuration.GreenFactory;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenMetricFacade;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.core.dto.project.GreenMetric;
import net.sf.esfinge.greenframework.core.mock.service.metrictest.MathService;
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
                        saved -> "net.sf.esfinge.greenframework.mock.service.metrictest.SumService#sum".equals(saved.getMethod())
                ).findFirst()
                .orElse(null);
        assertNotNull(greenMetric);
        assertEquals(countCallMethod , greenMetric.getCountCalled());
        //Value from CalculateLongMetricValue
        assertEquals(countCallMethod * 2.3 , greenMetric.getTotalSavedValue());
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
                        saved -> "net.sf.esfinge.greenframework.mock.service.metrictest.SumService#minus".equals(saved.getMethod())
                ).findFirst()
                .orElse(null);
        assertNotNull(greenMetric);
        assertEquals(countCallMethod , greenMetric.getCountCalled());
        //Value from CalculateLongMetricValue
        assertEquals(countCallMethod * 4.9 , greenMetric.getTotalSavedValue());
        assertEquals(4.9 , greenMetric.getMetricSavedValue());
    }
}
