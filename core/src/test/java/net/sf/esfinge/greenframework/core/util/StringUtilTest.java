package net.sf.esfinge.greenframework.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StringUtilTest {

    @Test
    void testToStringOrNull_WhenValueIsNotNull() {
        Object value = 123;
        String result = StringUtil.toStringOrNull(value);
        assertEquals("123", result);
    }

    @Test
    void testToStringOrNull_WhenValueIsNull() {
        String result = StringUtil.toStringOrNull(null);
        assertNull(result);
    }
}
