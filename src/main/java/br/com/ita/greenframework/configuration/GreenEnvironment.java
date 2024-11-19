package br.com.ita.greenframework.configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GreenEnvironment {

    public static String getPackage() {
        return Optional.ofNullable(getEnv("GREEN_SCAN_PACKAGE"))
                .orElse("br.com.ita.greenframework");
    }

    private static String getEnv(String env) {
        return System.getenv(env);
    }

}
