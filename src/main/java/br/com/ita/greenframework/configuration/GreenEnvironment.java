package br.com.ita.greenframework.configuration;

import br.com.ita.greenframework.util.GreenConstant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GreenEnvironment {

    public static String getPersistenceType() {
        return Optional.ofNullable(getEnv("GREEN_PERSISTENCE_TYPE"))
                .orElse(GreenConstant.PERSISTENCE_TYPE_MEMORY);
    }

    public static String getPackage() {
        return Optional.ofNullable(getEnv("GREEN_SCAN_PACKAGE"))
                .orElse(GreenConstant.DEFAULT_PACKAGE);
    }

    private static String getEnv(String env) {
        String value = System.getProperty(env);
        if (value == null) {
            value = System.getenv(env);
        }
        return value;
    }

}
