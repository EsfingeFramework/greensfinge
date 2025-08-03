package net.sf.esfinge.greenframework.core.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

    public static String toStringOrNull(Object value) {
        return Optional.ofNullable(value)
                .map(Object::toString)
                .orElse(null);
    }
}
