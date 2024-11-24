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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationMetricTest {

    @Test
    void testExecuteWithoutMock() throws Exception {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        String mockValue = UUID.randomUUID().toString();
        facade.setGeneralConfiguration(GreenOptionalConfiguration.builder()
                .ignore(false)
                .strDefaultValue(mockValue)
                .configurationKey("keyGroupService")
                .build());

        UserService userService = GreenFactory.greenify(UserService.class);
        Map<String, Object> returnService = userService.testIgnoreAnnotation();

        assertNotEquals(mockValue, returnService.get("test1"));
        assertEquals("GroupService - doSomething - Test", returnService.get("test1"));
        assertEquals(0, Integer.valueOf(returnService.get("test2").toString()));
        assertEquals(0, Integer.valueOf(returnService.get("test3").toString()));
        assertEquals(0, Long.valueOf(returnService.get("test4").toString()));

        GreenThreadLocal.cleanThreadLocalValue();
    }

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

        Integer mockValue = 5;
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

        GreenThreadLocal.cleanThreadLocalValue();
    }

}
