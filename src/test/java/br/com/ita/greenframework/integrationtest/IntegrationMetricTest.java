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

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationMetricTest {

    @Test
    void testSameMethodCall() throws Exception {
        GreenMetricFacade metricFacade = new GreenMetricFacade();

        UserService userService = GreenFactory.greenify(UserService.class);
        userService.testSameMethodCall();

        assertNull(metricFacade.getSavedEnergy().stream()
                .filter(e -> e.getMethod().contains("doSomething3"))
                .findAny()
                .orElse(null));
    }

    @Test
    void testSameMethodCallWithGreen() throws Exception {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        GreenMetricFacade metricFacade = new GreenMetricFacade();

        Integer mockValue = 7;
        String mockValueStr = "Mock Test";
        facade.setGeneralConfiguration(GreenNumberConfiguration.builder()
                .configurationKey("keyBeginCountPrimes")
                .value(mockValue)
                .build());

        facade.setGeneralConfiguration(GreenOptionalConfiguration.builder()
                .ignore(true)
                .strDefaultValue(mockValueStr)
                .configurationKey("keyGroupService")
                .build());

        UserService userService = GreenFactory.greenify(UserService.class);
        List<String> list = userService.testSameMethodCallWithGreen();

        list.forEach(e -> assertEquals(mockValueStr, e));

        GreenMetric greenMetric = metricFacade.getSavedEnergy().stream()
                .filter(e -> e.getMethod().contains("doSomething4"))
                .findAny()
                .orElse(null);

        assertNotNull(greenMetric);
        assertEquals(mockValue, greenMetric.getCountCalled());
        assertEquals(mockValue, list.size());
        assertEquals(456.787 * greenMetric.getCountCalled(), greenMetric.getSavedValue());

        GreenThreadLocal.cleanThreadLocalValue();
    }

}
