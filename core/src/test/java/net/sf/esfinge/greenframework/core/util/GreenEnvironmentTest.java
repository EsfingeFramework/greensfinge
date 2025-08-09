package net.sf.esfinge.greenframework.core.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreenEnvironmentTest {

    @AfterEach
    void cleanUp() {
        System.clearProperty("green.framework.persistence-type");
        System.clearProperty("green.framework.scan-package");
        System.clearProperty("green.framework.default-unit");
        System.clearProperty("green.framework.price-per-kwh");
        System.clearProperty("green.framework.co2-per-km-car");
        System.clearProperty("green.framework.co2-absorbed-per-tree-year");
        System.clearProperty("green.framework.power-lamp-led-watts");
    }

    @Test
    void testGetPersistenceType_Default() {
        System.clearProperty("green.framework.persistence-type");
        assertEquals(GreenConstant.PERSISTENCE_TYPE_MEMORY, GreenEnvironment.getPersistenceType());
    }

    @Test
    void testGetPersistenceType_Set() {
        System.setProperty("green.framework.persistence-type", "hibernate");
        assertEquals("hibernate", GreenEnvironment.getPersistenceType());
    }

    @Test
    void testGetDefaultPackage_Default() {
        System.clearProperty("green.framework.scan-package");
        assertEquals(GreenConstant.DEFAULT_PACKAGE, GreenEnvironment.getDefaultPackage());
    }

    @Test
    void testGetDefaultPackage_Set() {
        System.setProperty("green.framework.scan-package", "com.example");
        assertEquals("com.example", GreenEnvironment.getDefaultPackage());
    }

    @Test
    void testGetDefaultUnit_Default() {
        System.clearProperty("green.framework.default-unit");
        assertEquals(GreenConstant.DEFAULT_UNIT_JOULE, GreenEnvironment.getDefaultUnit());
    }

    @Test
    void testGetDefaultUnit_Set() {
        System.setProperty("green.framework.default-unit", "kWh");
        assertEquals("kWh", GreenEnvironment.getDefaultUnit());
    }

    @Test
    void testGetPricePerKwh_Default() {
        System.clearProperty("green.framework.price-per-kwh");
        assertEquals(0.0, GreenEnvironment.getPricePerKwh());
    }

    @Test
    void testGetPricePerKwh_Set() {
        System.setProperty("green.framework.price-per-kwh", "0.89");
        assertEquals(0.89, GreenEnvironment.getPricePerKwh());
    }

    @Test
    void testGetCo2PerKMCar_Default() {
        System.clearProperty("green.framework.co2-per-km-car");
        assertEquals(0.0, GreenEnvironment.getCo2PerKMCar());
    }

    @Test
    void testGetCo2PerKMCar_Set() {
        System.setProperty("green.framework.co2-per-km-car", "2.2");
        assertEquals(2.2, GreenEnvironment.getCo2PerKMCar());
    }

    @Test
    void testGetCo2AbsorbedPerTree_Default() {
        System.clearProperty("green.framework.co2-absorbed-per-tree-year");
        assertEquals(0.0, GreenEnvironment.getCo2AbsorbedPerTree());
    }

    @Test
    void testGetCo2AbsorbedPerTree_Set() {
        System.setProperty("green.framework.co2-absorbed-per-tree-year", "7.1");
        assertEquals(7.1, GreenEnvironment.getCo2AbsorbedPerTree());
    }

    @Test
    void testGetPowerLampLed_Default() {
        System.clearProperty("green.framework.power-lamp-led-watts");
        assertEquals(0.0, GreenEnvironment.getPowerLampLed());
    }

    @Test
    void testGetPowerLampLed_Set() {
        System.setProperty("green.framework.power-lamp-led-watts", "5.5");
        assertEquals(5.5, GreenEnvironment.getPowerLampLed());
    }
}
