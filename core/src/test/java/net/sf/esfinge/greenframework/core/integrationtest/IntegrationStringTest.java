package net.sf.esfinge.greenframework.core.integrationtest;

import net.sf.esfinge.greenframework.core.configuration.GreenFactory;
import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.core.mock.entity.User;
import net.sf.esfinge.greenframework.core.mock.service.stringtest.UserService;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationStringTest {

    private final UserService userService = GreenFactory.greenify(UserService.class);

    @Test
    void testShouldIgnoreCallWithGreenDefault() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        String mockValue = "Mocking a random value";
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                        .ignore(true)
                        .configurationKey("keyProfileService")
                        .defaultValue(mockValue)
                .build());

        String profile = userService.getUserProfileEmptyAnnotation();
        assertEquals(mockValue, profile);
    }

    @Test
    void testShouldIgnoreCallWithoutGreenDefault() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        String mockValue = "Mocking a random value";
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keyProfileService")
                .defaultValue(mockValue)
                .build());

        String profile = userService.getUserProfile();
        assertEquals(mockValue, profile);
    }

    @Test
    void testShouldIgnoreCallWithValueInAnnotationGreenAnnotation() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        String mockValue = "Mocking a random value";
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keyProfileService")
                .defaultValue(mockValue)
                .build());

        String profile = userService.getUserProfileWithValueAnnotation();
        assertEquals("Mock value in annotation", profile);
    }

    @Test
    void testShouldNotIgnoreCall() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        String mockValue = "Mocking a random value";
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("keyProfileService")
                .defaultValue(mockValue)
                .build());

        String profile = userService.getUserProfile();
        assertEquals("ProfileService - findProfile", profile);
    }

    @Test
    void testShouldNotFailWithChangeConfiguration() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        String mockValue = "Mocking a random value";
        for (int i = 0; i < 5; i++) {
            boolean random = new Random().nextBoolean();
            facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                    .ignore(random)
                    .configurationKey("keyProfileService")
                    .defaultValue(mockValue)
                    .build());

            String returnProfile = userService.getUserProfile();
            if(random) {
                assertEquals(mockValue, returnProfile);
            } else {
                assertEquals("ProfileService - findProfile", returnProfile);
            }
        }
    }

    @Test
    void testShouldCallMethodWithRuntimeException(){
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        String mockValue = "Mocking a random value";
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("keyProfileService")
                .defaultValue(mockValue)
                .build());

        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                userService::getProfileWithRuntimeError);
        assertEquals("Should not invoke this runtime error method", error.getMessage());
    }

    @Test
    void testShouldCallMethodWithException() throws Exception {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        String mockValue = "Mocking a random value";
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("keyProfileService")
                .defaultValue(mockValue)
                .build());

        Exception error = assertThrows(Exception.class,
                userService::getProfileWithError);
        assertEquals("Should not invoke this exception method", error.getMessage());
    }

    @Test
    void testShouldReturnComplexObjWithIgnore() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        String mockValue = "{\"name\":\"Mock\",\"countLogin\":5,\"proffile\":\"Mock Profile\"}";
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keyUserDao")
                .defaultValue(mockValue)
                .build());

        User user = userService.getUser();
        assertNotNull(user);
        assertEquals("Mock", user.getName());
        assertEquals(5, user.getCountLogin());
        assertNull(user.getProfile());
    }

    @Test
    void testShouldReturnComplexObjWithoutIgnore() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        String mockValue = "{\"name\":\"Mock\",\"countLogin\":5,\"proffile\":\"Mock Profile\"}";
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("keyUserDao")
                .defaultValue(mockValue)
                .build());

        User user = userService.getUser();
        assertNotNull(user);
        assertNull(user.getName());
        assertNull(user.getCountLogin());
        assertNull(user.getProfile());
    }

    @Test
    void testShouldReturnComplexObjWithIgnoreAnnotation() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        String mockValue = "{\"name\":\"Mock\",\"countLogin\":5,\"proffile\":\"Mock Profile\"}";
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keyUserDao")
                .defaultValue(mockValue)
                .build());

        User user = userService.getUserWithAnnotation();
        assertNotNull(user);
        assertEquals("Mock Annotation", user.getName());
        assertEquals(2 , user.getCountLogin());
        assertEquals("Mock Profile Annotation", user.getProfile());
    }

    @Test
    void testShouldReturnGreenValueInsideMethod() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keyMethodConfig")
                .build());

        String returnStr = userService.geGreenValueInsideMethodClass();
        assertNotNull(returnStr);
        assertEquals("Method inside class return", returnStr);
    }

    @Test
    void testShouldReturnGreenValueInsideMethodNotIgnoreExecution() {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("keyMethodConfig")
                .build());

        String returnStr = userService.geGreenValueInsideMethodClass();
        assertNotNull(returnStr);
        assertEquals("", returnStr);
    }

}
