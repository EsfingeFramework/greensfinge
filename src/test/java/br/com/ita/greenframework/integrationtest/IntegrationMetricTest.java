package br.com.ita.greenframework.integrationtest;

import br.com.ita.greenframework.configuration.GreenFactory;
import br.com.ita.greenframework.configuration.facade.GreenConfigurationFacade;
import br.com.ita.greenframework.configuration.facade.GreenMetricFacade;
import br.com.ita.greenframework.dto.annotation.GreenNumberConfiguration;
import br.com.ita.greenframework.dto.annotation.GreenOptionalConfiguration;
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
    void testShouldMetricSeveralCall() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        GreenMetricFacade metricFacade = new GreenMetricFacade();

        Long mockValue1 = 2L;
        Integer mockValue2 = 8;
        int countCallMethod = 15;

        facade.setGeneralConfiguration(GreenNumberConfiguration.builder()
                .configurationKey("keyNumber4")
                .value(mockValue1)
                .build());

        facade.setGeneralConfiguration(GreenNumberConfiguration.builder()
                .configurationKey("keyNumber3")
                .value(mockValue2)
                .build());

        facade.setGeneralConfiguration(GreenOptionalConfiguration.builder()
                .ignore(true)
                .configurationKey("keySumService")
                .numberDefaultValue(45D)
                .build());

        for (int i = 0; i < countCallMethod; i++) {
            assertEquals(45, mathService.countNumber4());
        }

        GreenMetric greenMetric = metricFacade.getSavedEnergy().stream().filter(
                        saved -> "br.com.ita.greenframework.mock.service.metrictest.SumService#sum".equals(saved.getMethod())
                ).findFirst()
                .orElse(null);
        assertNotNull(greenMetric);
        assertEquals(countCallMethod , greenMetric.getCountCalled());
        assertEquals(15.0 , greenMetric.getSavedValue());
        assertEquals(1.0 , greenMetric.getMetricSavedValue());
    }
}
