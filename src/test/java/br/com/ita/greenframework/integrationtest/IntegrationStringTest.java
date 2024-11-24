package br.com.ita.greenframework.integrationtest;

import br.com.ita.greenframework.configuration.GreenFactory;
import br.com.ita.greenframework.configuration.GreenThreadLocal;
import br.com.ita.greenframework.configuration.facade.GreenConfigurationFacade;
import br.com.ita.greenframework.configuration.facade.GreenMetricFacade;
import br.com.ita.greenframework.dto.annotation.GreenNumberConfiguration;
import br.com.ita.greenframework.dto.annotation.GreenOptionalConfiguration;
import br.com.ita.greenframework.dto.project.GreenMetric;
import br.com.ita.greenframework.mockservice.UserService;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IntegrationStringTest {

    @Test
    void testIgnoreStrAnnotation() throws Exception {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        GreenMetricFacade metricFacade = new GreenMetricFacade();

        String mockValue = "Mock Test";
        facade.setGeneralConfiguration(GreenOptionalConfiguration.builder()
                .ignore(true)
                .strDefaultValue(mockValue)
                .configurationKey("keyGroupService")
                .build());

        UserService userService = GreenFactory.greenify(UserService.class);
        Map<String, Object> returnService = userService.testIgnoreAnnotation();

        assertEquals(mockValue, returnService.get("test1"));
        assertEquals(0, Integer.valueOf(returnService.get("test2").toString()));
        assertEquals(0, Integer.valueOf(returnService.get("test3").toString()));

        GreenMetric greenMetric = metricFacade.getSavedEnergy().stream()
                .filter(e -> e.getMethod().contains("doSomething"))
                .findAny()
                .orElse(null);

        assertNotNull(greenMetric);
        assertEquals(1, greenMetric.getCountCalled());

        GreenThreadLocal.cleanThreadLocalValue();
    }

    @Test
    void testIgnoreDefaultValueInAnnotation() throws Exception {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        facade.setGeneralConfiguration(GreenOptionalConfiguration.builder()
                .ignore(true)
                .strDefaultValue("mockValue")
                .numberDefaultValue(78)
                .configurationKey("keyGroupServiceMock")
                .build());

        UserService userService = GreenFactory.greenify(UserService.class);
        Map<String, Object> returnService = userService.testMockValueAnnotation();

        assertEquals("Mock value from annotation", returnService.get("test1").toString());
        assertEquals(998756424, Integer.valueOf(returnService.get("test2").toString()));

        GreenThreadLocal.cleanThreadLocalValue();
    }
}
