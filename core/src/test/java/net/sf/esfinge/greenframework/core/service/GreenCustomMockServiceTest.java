package net.sf.esfinge.greenframework.core.service;

import net.sf.esfinge.greenframework.core.configuration.mockreturn.GreenCustomMockProvider;
import net.sf.esfinge.greenframework.core.dao.contract.GreenCustomMockDao;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;
import net.sf.esfinge.greenframework.core.entity.enuns.Messages;
import net.sf.esfinge.greenframework.core.exception.GreenBusinessException;
import net.sf.esfinge.greenframework.core.helper.ReflectionHelperTest;
import net.sf.esfinge.greenframework.core.identity.GreenIdentityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.sf.esfinge.greenframework.core.helper.EasyRandomHelperTest.nextObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GreenCustomMockServiceTest {

    @Mock
    private GreenCustomMockDao daoMock;

    @InjectMocks
    private GreenCustomMockService service;

    @BeforeEach
    void setup() {
        ReflectionHelperTest.injectValue(service, "customMockDao", daoMock);
    }

    @Test
    void testCreateCustomMock_insert() {
        GreenCustomMockConfiguration mockConfig = createMockConfig();

        doReturn(Optional.empty())
                .when(daoMock).findByKey(anyString());

        service.createCustomMock(mockConfig);

        verify(daoMock).insertGeneralConfiguration(mockConfig);
        verify(daoMock, never()).updateGeneralConfiguration(any());
    }

    @Test
    void testCreateCustomMock_update() {
        GreenCustomMockConfiguration mockConfig = createMockConfig();

        doReturn(Optional.of(GreenCustomMockConfiguration.builder().build()))
                .when(daoMock).findByKey(anyString());

        service.createCustomMock(mockConfig);

        verify(daoMock).updateGeneralConfiguration(mockConfig);
        verify(daoMock, never()).insertGeneralConfiguration(any());
    }

    @Test
    void testCreateCustomMock_invalidJson_throws() {
        GreenCustomMockConfiguration mockConfig = nextObject(GreenCustomMockConfiguration.class);

        GreenBusinessException ex = assertThrows(GreenBusinessException.class, () -> {
            service.createCustomMock(mockConfig);
        });

        assertEquals(ex.getMessage(), Messages.ERROR_CUSTOM_MOCK_DEFAULT_VALUE.getMessage(mockConfig.getDefaultValue()));
    }

    @Test
    void testCreateCustomMock_invalidInstance_throws() {
        GreenCustomMockConfiguration mockConfig = createMockConfig();
        String customClass = "net.sf.esfinge.greenframework.core.service.GreenCustomMockServiceTest$DummyCustomMockConfigurationNotCustomProvider";
        mockConfig.setCustomClass(customClass);

        GreenBusinessException ex = assertThrows(GreenBusinessException.class, () -> {
            service.createCustomMock(mockConfig);
        });
        assertEquals(ex.getMessage(), Messages.ERROR_CUSTOM_MOCK_NOT_INSTANCE.getMessage("class " + customClass));
    }

    @Test
    void testCreateCustomMock_invalidReturnType_throws() {
        GreenCustomMockConfiguration mockConfig = createMockConfig();
        String customClass = "net.sf.esfinge.greenframework.core.service.GreenCustomMockServiceTest$DummyCustomMockConfigurationDifferentReturnType";
        mockConfig.setCustomClass(customClass);

        GreenBusinessException ex = assertThrows(GreenBusinessException.class, () -> {
            service.createCustomMock(mockConfig);
        });

        assertEquals(ex.getMessage(), Messages.ERROR_CUSTOM_MOCK_RETURN_TYPE.getMessage(mockConfig.getReturnType(), customClass, GreenIdentityProvider.class.getName()));
    }

    @Test
    void testGetAllConfigurations_delegates() {
        List<GreenCustomMockConfiguration> list = List.of(nextObject(GreenCustomMockConfiguration.class));
        when(daoMock.getAllConfigurations()).thenReturn(list);

        List<GreenCustomMockConfiguration> result = service.getAllConfigurations();

        assertSame(list, result);
        verify(daoMock).getAllConfigurations();
    }

    @Test
    void testFindByReturnType_delegates() {
        GreenCustomMockConfiguration mockConfig = nextObject(GreenCustomMockConfiguration.class);

        doReturn(mockConfig)
                .when(daoMock).findByReturnType(anyString());

        GreenCustomMockConfiguration result = service.findByReturnType("type");

        assertSame(mockConfig, result);
        verify(daoMock).findByReturnType("type");
    }

    private GreenCustomMockConfiguration createMockConfig() {
        return GreenCustomMockConfiguration.builder()
                .customClass("net.sf.esfinge.greenframework.core.service.GreenCustomMockServiceTest$DummyCustomMockConfiguration")
                .key("dummyKey")
                .defaultValue("{\"value\":\"some_value\"}")
                .returnType("net.sf.esfinge.greenframework.core.identity.GreenIdentityProvider")
                .build();
    }

    static class DummyCustomMockConfiguration implements GreenCustomMockProvider {

        @Override
        public Object processCustomMockReturn(GreenCustomMockConfiguration customMockConfiguration, Map<String, Object> defaultValue) {
            return new GreenIdentityProvider() {
                @Override
                public String getKeyContext() {
                    return defaultValue.get("value").toString();
                }
            };
        }
    }

    static class DummyCustomMockConfigurationNotCustomProvider {

        public Object processCustomMockReturn(GreenCustomMockConfiguration customMockConfiguration, Map<String, Object> defaultValue) {
            return new GreenIdentityProvider() {
                @Override
                public String getKeyContext() {
                    return defaultValue.get("value").toString();
                }
            };
        }
    }

    static class DummyCustomMockConfigurationDifferentReturnType implements GreenCustomMockProvider {

        @Override
        public Object processCustomMockReturn(GreenCustomMockConfiguration customMockConfiguration, Map<String, Object> defaultValue) {
            return String.valueOf(defaultValue.get("value"));
        }
    }

}
