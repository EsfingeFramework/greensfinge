package br.com.ita.greenframework.integrationtest;

import br.com.ita.greenframework.configuration.GreenFactory;
import br.com.ita.greenframework.configuration.facade.GreenConfigurationFacade;
import br.com.ita.greenframework.dto.annotation.GreenOptionalConfiguration;
import br.com.ita.greenframework.mockservice.UserService;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IntegrationWithoutGreenTest {

    @Test
    void testExecuteWithoutGreen() throws Exception {
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
    }
}
