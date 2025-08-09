package net.sf.esfinge.greenframework.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonUtilTest {

    @Test
    void testIsValid_ValidJson() {
        String json = "{\"name\":\"GreenFramework\",\"version\":1}";
        assertTrue(JsonUtil.isValid(json));
    }

    @Test
    void testIsValid_InvalidJson() {
        String invalidJson = "{name:GreenFramework, version:1";
        assertFalse(JsonUtil.isValid(invalidJson));
    }

    @Test
    void testIsValid_Null() {
        assertFalse(JsonUtil.isValid(null));
    }

    @Test
    void testIsValid_EmptyString() {
        assertTrue(JsonUtil.isValid(""));
    }
}
