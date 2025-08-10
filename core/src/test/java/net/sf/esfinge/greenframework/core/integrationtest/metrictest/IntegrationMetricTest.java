package net.sf.esfinge.greenframework.core.integrationtest.metrictest;

import net.sf.esfinge.greenframework.core.configuration.GreenFactory;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenMetricFacade;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.core.dto.project.GreenMetricResponse;
import net.sf.esfinge.greenframework.core.integrationtest.metrictest.mock.MathService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IntegrationMetricTest {

    private final MathService mathService = GreenFactory.greenify(MathService.class);

    @Test
    void testShouldMetricCustomEnergyWithSeveralCall() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        GreenMetricFacade metricFacade = new GreenMetricFacade();

        Double mockMethodReturn = 45D;

        int countCallMethod = 15;

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("SumService#sum")
                .defaultValue(mockMethodReturn.toString())
                .build());

        for (int i = 0; i < countCallMethod; i++) {
            assertEquals(mockMethodReturn.intValue(), mathService.countNumber4());
        }

        GreenMetricResponse greenMetric = metricFacade.getSavedEnergy(true).getMethods().stream().filter(
                        saved -> "net.sf.esfinge.greenframework.core.integrationtest.metrictest.mock.SumService#sum".equals(saved.getMethod())
                ).findFirst()
                .orElse(null);
        assertNotNull(greenMetric);
        assertEquals(countCallMethod , greenMetric.getCountCalled());
        assertEquals(countCallMethod * 2.3 , greenMetric.getTotalSavedValue());
        assertEquals(countCallMethod , greenMetric.getTraces().size());
        assertEquals(2.3 , greenMetric.getTraces().get(0).getMetricSavedValue());

    }

    @Test
    void testShouldMetricFixedEnergyWithSeveralCall() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        GreenMetricFacade metricFacade = new GreenMetricFacade();

        int countCallMethod = 15;

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("SumService#minus")
                .build());

        for (int i = 0; i < countCallMethod; i++) {
            assertEquals(8, mathService.minusOperation());
        }

        GreenMetricResponse greenMetric = metricFacade.getSavedEnergy(true).getMethods().stream().filter(
                        saved -> "net.sf.esfinge.greenframework.core.integrationtest.metrictest.mock.SumService#minus".equals(saved.getMethod())
                ).findFirst()
                .orElse(null);
        assertNotNull(greenMetric);
        assertEquals(countCallMethod , greenMetric.getCountCalled());
        //Value from CalculateLongMetricValue
        assertEquals(countCallMethod * 4.9 , greenMetric.getTotalSavedValue());
        assertEquals(countCallMethod , greenMetric.getTraces().size());
        assertEquals(4.9 , greenMetric.getTraces().get(0).getMetricSavedValue());
    }
}
