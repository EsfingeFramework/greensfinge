package net.sf.esfinge.greenframework.core.configuration.mockreturn;

import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;

import java.util.Map;

public interface GreenCustomMockProvider {

    Object processCustomMockReturn(GreenCustomMockConfiguration customMockConfiguration, Map<String, Object> defaultValue);
}
