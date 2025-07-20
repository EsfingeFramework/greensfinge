package net.sf.esfinge.greenframework.core.integrationtest;

import net.sf.esfinge.greenframework.core.configuration.GreenFactory;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.core.mock.service.voidtest.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegrationVoidTest {

    private final UserService userService = GreenFactory.greenify(UserService.class);

    @Test
    void testShouldIgnoreExecution() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keyProfileService")
                .strDefaultValue("")
                .build());

        String profile = userService.findUser();
        assertEquals(" initial value  final value ", profile);
    }

    @Test
    void testShouldExecuteWithMockValue() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keyProfileService")
                .strDefaultValue("some value in the middle")
                .build());

        String profile = userService.findUser();
        assertEquals(" initial value some value in the middle final value ", profile);
    }

    @Test
    void testShouldExecuteWithNoIgnore() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("keyProfileService")
                .build());

        String profile = userService.findUser();
        assertEquals(" initial value Some profile final value ", profile);
    }

    @Test
    void testShouldExecutionHighConsumeEnergy() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("methodKeyConfig")
                .build());

        StringBuilder sbParam = new StringBuilder();
        sbParam.append("testValue");
        userService.doSomethingWithHighConsumeEnergy(sbParam);
        assertEquals("testValuesomething very high", sbParam.toString());

        System.out.println(sbParam.toString());
    }
}
