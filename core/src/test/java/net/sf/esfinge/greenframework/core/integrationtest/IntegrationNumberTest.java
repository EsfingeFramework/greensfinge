package net.sf.esfinge.greenframework.core.integrationtest;

import net.sf.esfinge.greenframework.core.configuration.GreenFactory;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenAdjustableNumberConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.core.mock.service.numbertest.FileService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IntegrationNumberTest {

    private final FileService fileService = GreenFactory.greenify(FileService.class);

    @Test
    void testShouldChangeIntegerValueBasedConfiguration() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        Integer mockValue = 2;
        facade.setGeneralConfiguration(GreenAdjustableNumberConfiguration.builder()
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
        facade.setGeneralConfiguration(GreenAdjustableNumberConfiguration.builder()
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
        facade.setGeneralConfiguration(GreenAdjustableNumberConfiguration.builder()
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
        facade.setGeneralConfiguration(GreenAdjustableNumberConfiguration.builder()
                .configurationKey("keyFileLength")
                .value(mockValue)
                .build());

        Long countTry = fileService.getDefaultFileLength();
        assertEquals(5, countTry);
    }

    @Test
    void testShouldReturnGreenValueInsideMethod() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("method1KeyConfiguration")
                .build());

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("method2KeyConfiguration")
                .build());

        Integer returnInt1 = fileService.getGreenValueInsideMethodClass1();

        assertNotNull(returnInt1);
        assertEquals(6458, returnInt1);

        Integer returnInt2 = fileService.getGreenValueInsideMethodClass2();

        assertNotNull(returnInt2);
        assertEquals(6459, returnInt2);
    }

    @Test
    void testShouldReturnGreenValueInsideMethodWithoutIgnoreExecution() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("method1KeyConfiguration")
                .build());

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("method2KeyConfiguration")
                .build());

        Integer returnInt1 = fileService.getGreenValueInsideMethodClass1();

        assertNotNull(returnInt1);
        assertEquals(0, returnInt1);

        Integer returnInt2 = fileService.getGreenValueInsideMethodClass2();

        assertNotNull(returnInt2);
        assertEquals(0, returnInt2);
    }
}
