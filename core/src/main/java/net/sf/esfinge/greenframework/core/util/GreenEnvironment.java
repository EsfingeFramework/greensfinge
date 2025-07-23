package net.sf.esfinge.greenframework.core.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GreenEnvironment {

    public static String getPersistenceType() {
        return Optional.ofNullable(getEnv("green.framework.persistence-type"))
                .orElse(GreenConstant.PERSISTENCE_TYPE_MEMORY);
    }

    public static String getDefaultPackage() {
        return Optional.ofNullable(getEnv("green.framework.scan-package"))
                .orElse(GreenConstant.DEFAULT_PACKAGE);
    }

    public static String getDefaultUnit() {
        return Optional.ofNullable(getEnv("green.framework.default-unit"))
                .orElse(GreenConstant.DEFAULT_UNIT_JOULE);
    }

    public static Double getPricePerKwh() {
        return Optional.ofNullable(getEnv("green.framework.price-per-kwh"))
                .map(Double::valueOf)
                .orElse(0.0);
    }

    private static String getEnv(String env) {
        String value = System.getProperty(env);
        if (value == null) {
            value = System.getenv(env);
        }
        return value;
    }

}
