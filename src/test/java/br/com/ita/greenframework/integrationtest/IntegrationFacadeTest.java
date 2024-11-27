package br.com.ita.greenframework.integrationtest;

import br.com.ita.greenframework.configuration.facade.GreenConfigurationFacade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(value = TestInstance.Lifecycle.PER_METHOD)
class IntegrationFacadeTest {

    @Test
    void testGetConfiguration() {
        GreenConfigurationFacade configurationFacade = new GreenConfigurationFacade();

        System.setProperty("GREEN_SCAN_PACKAGE", "br.com.ita.greenframework.mockservice.facade");
        Assertions.assertEquals(2 , configurationFacade.getConfigurationsInProject().size());
    }

}
