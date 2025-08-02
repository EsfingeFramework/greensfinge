package net.sf.esfinge.greenframework.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {

    public static boolean isValid(String jsonString) {
        boolean isValid = true;
        try {
            new ObjectMapper().readTree(jsonString);
        } catch (IOException e) {
            isValid = false;
        }
        return isValid;
    }
}
