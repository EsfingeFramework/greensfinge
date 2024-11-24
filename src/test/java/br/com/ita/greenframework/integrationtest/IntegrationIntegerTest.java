package br.com.ita.greenframework.integrationtest;

import br.com.ita.greenframework.configuration.GreenFactory;
import br.com.ita.greenframework.configuration.GreenThreadLocal;
import br.com.ita.greenframework.configuration.facade.GreenConfigurationFacade;
import br.com.ita.greenframework.dto.annotation.GreenNumberConfiguration;
import br.com.ita.greenframework.mockservice.UserService;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegrationIntegerTest {

    @Test
    void testGreenIntegerAnnotation() throws Exception {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        Integer mockValue1 = 2;
        Integer mockValue2 = 100;

        facade.setGeneralConfiguration(GreenNumberConfiguration.builder()
                .configurationKey("keyBeginCountPrimes")
                .value(mockValue1)
                .build());

        facade.setGeneralConfiguration(GreenNumberConfiguration.builder()
                .configurationKey("keyEndCountPrimes")
                .value(mockValue2)
                .build());

        UserService userService = GreenFactory.greenify(UserService.class);
        Map<String, Object> returnService = userService.testIgnoreAnnotation();

        assertEquals("GroupService - doSomething - Test", returnService.get("test1"));
        assertEquals(mockValue1, Integer.valueOf(returnService.get("test2").toString()));
        assertEquals(mockValue2, Integer.valueOf(returnService.get("test3").toString()));
        assertEquals(25, Long.valueOf(returnService.get("test4").toString()));

        GreenThreadLocal.cleanThreadLocalValue();
    }

}
