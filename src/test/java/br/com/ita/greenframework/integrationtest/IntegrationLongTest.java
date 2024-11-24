package br.com.ita.greenframework.integrationtest;

import br.com.ita.greenframework.configuration.GreenFactory;
import br.com.ita.greenframework.configuration.facade.GreenConfigurationFacade;
import br.com.ita.greenframework.dto.annotation.GreenOptionalConfiguration;
import br.com.ita.greenframework.mockservice.UserService;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegrationLongTest {

    @Test
    void testGreenLongAnnotation() throws Exception {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        Integer mockValue1 = new Random().nextInt();
        facade.setGeneralConfiguration(GreenOptionalConfiguration.builder()
                .ignore(true)
                .configurationKey("keyMathService")
                .numberDefaultValue(mockValue1)
                .build());

        UserService userService = GreenFactory.greenify(UserService.class);
        Map<String, Object> returnService = userService.testIgnoreAnnotation();

        assertEquals("GroupService - doSomething - Test", returnService.get("test1"));
        assertEquals(0, Integer.valueOf(returnService.get("test2").toString()));
        assertEquals(0, Integer.valueOf(returnService.get("test3").toString()));
        assertEquals(mockValue1.longValue(), Long.valueOf(returnService.get("test4").toString()));
    }
}
