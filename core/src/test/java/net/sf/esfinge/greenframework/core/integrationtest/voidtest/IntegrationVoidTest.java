package net.sf.esfinge.greenframework.core.integrationtest.voidtest;

import net.sf.esfinge.greenframework.core.configuration.GreenFactory;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.core.integrationtest.voidtest.mock.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class IntegrationVoidTest {

    private final UserService userService = GreenFactory.greenify(UserService.class);

    @Test
    void testShouldNotIgnoreExecution() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("ProfileService#findProfile")
                .defaultValue("")
                .build());

        String profile = userService.findUser();
        assertEquals(" initial value Some profile final value ", profile);
    }

    @Test
    void testShouldIgnoreExecution() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("ProfileService#findProfile")
                .defaultValue("some value in the middle")
                .build());

        String profile = userService.findUser();
        assertEquals(" initial value some value in the middle final value ", profile);
    }

    @Test
    void testShouldNotMockExecutionHighConsumeEnergy() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("methodKeyConfig")
                .build());

        StringBuilder sbParam = new StringBuilder();
        sbParam.append("testValue");
        userService.doSomethingWithHighConsumeEnergy(sbParam);
        assertEquals("testValuesomething very high", sbParam.toString());
    }

    @Test
    void testShouldMockExecutionHighConsumeEnergy() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("methodKeyConfig")
                .build());

        StringBuilder sbParam = new StringBuilder();
        sbParam.append("testValue");
        userService.doSomethingWithHighConsumeEnergy(sbParam);
        assertEquals("testValue", sbParam.toString());
    }
}
