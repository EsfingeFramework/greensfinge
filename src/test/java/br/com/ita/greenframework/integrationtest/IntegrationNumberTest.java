package br.com.ita.greenframework.integrationtest;

import br.com.ita.greenframework.configuration.GreenFactory;
import br.com.ita.greenframework.configuration.facade.GreenConfigurationFacade;
import br.com.ita.greenframework.dto.annotation.GreenNumberConfiguration;
import br.com.ita.greenframework.mock.service.numbertest.FileService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegrationNumberTest {

    private final FileService fileService = GreenFactory.greenify(FileService.class);

    @Test
    void testShouldChangeIntegerValueBasedConfiguration() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        Integer mockValue = 2;
        facade.setGeneralConfiguration(GreenNumberConfiguration.builder()
                .configurationKey("keyCountTimes")
                .value(mockValue)
                .build());

        Integer countTry = fileService.tryAccessExternalFileWithGreen();
        assertEquals(mockValue, countTry);
    }

    @Test
    void testShouldNotChangeIntegerValueBasedConfiguration() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        Integer mockValue = 2;
        facade.setGeneralConfiguration(GreenNumberConfiguration.builder()
                .configurationKey("keyCountTimes")
                .value(mockValue)
                .build());

        Integer countTry = fileService.tryAccessExternalFileWithoutGreen();
        assertEquals(5, countTry);
    }

    @Test
    void testShouldChangeLongValueBasedConfiguration() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        Long mockValue = 2L;
        facade.setGeneralConfiguration(GreenNumberConfiguration.builder()
                .configurationKey("keyFileLength")
                .value(mockValue)
                .build());

        Long countTry = fileService.getFileLength();
        assertEquals(mockValue, countTry);
    }

    @Test
    void testShouldNotChangeLongBasedConfiguration() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        Long mockValue = 2L;
        facade.setGeneralConfiguration(GreenNumberConfiguration.builder()
                .configurationKey("keyFileLength")
                .value(mockValue)
                .build());

        Long countTry = fileService.getDefaultFileLength();
        assertEquals(5, countTry);
    }
}
