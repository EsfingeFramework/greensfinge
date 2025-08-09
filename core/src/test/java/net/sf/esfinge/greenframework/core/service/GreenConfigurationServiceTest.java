package net.sf.esfinge.greenframework.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.sf.esfinge.greenframework.core.dao.contract.GreenConfigurationDao;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenAdjustableNumberConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenDefaultConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.core.entity.GreenConfiguration;
import net.sf.esfinge.greenframework.core.helper.ReflectionHelperTest;
import net.sf.esfinge.greenframework.core.identity.GreenIdentityHolder;
import net.sf.esfinge.greenframework.core.identity.GreenIdentityProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static net.sf.esfinge.greenframework.core.helper.EasyRandomHelperTest.nextObject;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GreenConfigurationServiceTest {

    @Mock
    private GreenConfigurationDao daoMock;

    @InjectMocks
    private GreenConfigurationService service;

    @BeforeEach
    void setup() {
        ReflectionHelperTest.injectValue(service, "configurationDao", daoMock);
    }

    @Test
    void testSetGeneralConfiguration_Insert() {
        GreenSwitchConfiguration config = nextObject(GreenSwitchConfiguration.class);

        doReturn(Optional.empty())
                .when(daoMock).findGeneralConfiguration(anyString());

        service.setGeneralConfiguration("key", config);

        verify(daoMock).insertGeneralConfiguration(config);
        verify(daoMock, never()).updateGeneralConfiguration(any());
    }

    @Test
    void testSetGeneralConfiguration_Update() {
        GreenSwitchConfiguration config = nextObject(GreenSwitchConfiguration.class);

        doReturn(Optional.of(GreenConfiguration.builder().build()))
                .when(daoMock).findGeneralConfiguration(anyString());

        service.setGeneralConfiguration("key", config);

        verify(daoMock).updateGeneralConfiguration(config);
        verify(daoMock, never()).insertGeneralConfiguration(any());
    }

    @Test
    void testSetPersonalConfiguration_Insert() {
        GreenSwitchConfiguration config = nextObject(GreenSwitchConfiguration.class);

        doReturn(Optional.empty())
                .when(daoMock).findPersonalConfiguration(anyString(), any());

        service.setPersonalConfiguration("key", config);

        verify(daoMock).insertPersonalConfiguration(config);
        verify(daoMock, never()).updatePersonalConfiguration(any());
    }

    @Test
    void testSetPersonalConfiguration_Update() {
        GreenSwitchConfiguration config = nextObject(GreenSwitchConfiguration.class);

        doReturn(Optional.of(GreenConfiguration.builder().build()))
                .when(daoMock).findPersonalConfiguration(anyString(), any());

        service.setPersonalConfiguration("key", config);

        verify(daoMock).updatePersonalConfiguration(config);
        verify(daoMock, never()).insertPersonalConfiguration(any());
    }

    @Test
    @SneakyThrows
    void testGetSwitchConfigurationGeneralByType_Found() {
        GreenSwitchConfiguration greenConfiguration = nextObject(GreenSwitchConfiguration.class);
        GreenConfiguration greenConfig = GreenConfiguration.builder()
                .key(greenConfiguration.getConfigurationKey())
                .configurations(new ObjectMapper().writeValueAsString(greenConfiguration))
                .build();

        doReturn(Optional.of(greenConfig))
                .when(daoMock).getConfiguration(anyString(), any());

        try (MockedStatic<GreenIdentityHolder> mockedHolder = mockStatic(GreenIdentityHolder.class)) {
            GreenIdentityProvider providerMock = mock(GreenIdentityProvider.class);

            mockedHolder.when(GreenIdentityHolder::get)
                    .thenReturn(providerMock);
            when(providerMock.getKeyContext())
                    .thenReturn(null);

            GreenSwitchConfiguration result = service.getConfigurationByType(greenConfig.getKey(), GreenSwitchConfiguration.class);
            assertNotNull(result);
            assertEquals(greenConfig.getKey(), result.getConfigurationKey());
            verify(daoMock).getConfiguration(anyString(), isNull());
        }
    }

    @Test
    @SneakyThrows
    void testGetSwitchConfigurationPersonalByType_Found() {
        String anyKeyContext = nextObject(String.class);
        GreenSwitchConfiguration greenConfiguration = nextObject(GreenSwitchConfiguration.class);
        GreenConfiguration greenConfig = GreenConfiguration.builder()
                .key(greenConfiguration.getConfigurationKey())
                .keyContext(greenConfiguration.getKeyContext())
                .configurations(new ObjectMapper().writeValueAsString(greenConfiguration))
                .build();

        doReturn(Optional.of(greenConfig))
                .when(daoMock).getConfiguration(anyString(), any());

        try (MockedStatic<GreenIdentityHolder> mockedHolder = mockStatic(GreenIdentityHolder.class)) {
            GreenIdentityProvider providerMock = mock(GreenIdentityProvider.class);

            mockedHolder.when(GreenIdentityHolder::get)
                    .thenReturn(providerMock);
            when(providerMock.getKeyContext())
                    .thenReturn(anyKeyContext);

            GreenSwitchConfiguration result = service.getConfigurationByType(greenConfig.getKey(), GreenSwitchConfiguration.class);
            assertNotNull(result);
            assertEquals(greenConfig.getKey(), result.getConfigurationKey());
            assertEquals(greenConfig.getKeyContext(), result.getKeyContext());
            verify(daoMock).getConfiguration(anyString(), eq(anyKeyContext));
        }
    }

    @Test
    @SneakyThrows
    void testGetAdjustableNumberConfigurationGeneralByType_Found() {
        GreenAdjustableNumberConfiguration greenConfiguration = GreenAdjustableNumberConfiguration.builder()
                .configurationKey("dummyKey")
                .value(1)
                .build();
        GreenConfiguration greenConfig = GreenConfiguration.builder()
                .key(greenConfiguration.getConfigurationKey())
                .configurations(new ObjectMapper().writeValueAsString(greenConfiguration))
                .build();

        doReturn(Optional.of(greenConfig))
                .when(daoMock).getConfiguration(anyString(), any());

        try (MockedStatic<GreenIdentityHolder> mockedHolder = mockStatic(GreenIdentityHolder.class)) {
            GreenIdentityProvider providerMock = mock(GreenIdentityProvider.class);

            mockedHolder.when(GreenIdentityHolder::get)
                    .thenReturn(providerMock);
            when(providerMock.getKeyContext())
                    .thenReturn(null);

            GreenAdjustableNumberConfiguration result = service.getConfigurationByType(greenConfig.getKey(), GreenAdjustableNumberConfiguration.class);
            assertNotNull(result);
            assertEquals(greenConfiguration.getValue(), result.getValue());
            assertEquals(greenConfig.getKey(), result.getConfigurationKey());
            verify(daoMock).getConfiguration(anyString(), isNull());
        }
    }

    @Test
    @SneakyThrows
    void testGetAdjustableNumberConfigurationPersonalByType_Found() {
        String anyKeyContext = nextObject(String.class);

        GreenAdjustableNumberConfiguration greenConfiguration = GreenAdjustableNumberConfiguration.builder()
                .configurationKey("dummyKey")
                .value(1)
                .build();
        GreenConfiguration greenConfig = GreenConfiguration.builder()
                .key(greenConfiguration.getConfigurationKey())
                .keyContext(greenConfiguration.getKeyContext())
                .configurations(new ObjectMapper().writeValueAsString(greenConfiguration))
                .build();

        doReturn(Optional.of(greenConfig))
                .when(daoMock).getConfiguration(anyString(), any());

        try (MockedStatic<GreenIdentityHolder> mockedHolder = mockStatic(GreenIdentityHolder.class)) {
            GreenIdentityProvider providerMock = mock(GreenIdentityProvider.class);

            mockedHolder.when(GreenIdentityHolder::get)
                    .thenReturn(providerMock);
            when(providerMock.getKeyContext())
                    .thenReturn(anyKeyContext);

            GreenAdjustableNumberConfiguration result = service.getConfigurationByType(greenConfig.getKey(), GreenAdjustableNumberConfiguration.class);
            assertNotNull(result);
            assertEquals(greenConfig.getKey(), result.getConfigurationKey());
            assertEquals(greenConfig.getKeyContext(), result.getKeyContext());
            assertEquals(greenConfiguration.getValue(), result.getValue());
            verify(daoMock).getConfiguration(anyString(), eq(anyKeyContext));
        }
    }

    @Test
    void testGetConfigurationGeneralByType_NotFound() {
        doReturn(Optional.empty())
                .when(daoMock).getConfiguration(anyString(), any());

        try (MockedStatic<GreenIdentityHolder> mockedHolder = mockStatic(GreenIdentityHolder.class)) {
            GreenIdentityProvider providerMock = mock(GreenIdentityProvider.class);
            mockedHolder.when(GreenIdentityHolder::get).thenReturn(providerMock);
            when(providerMock.getKeyContext()).thenReturn(null);

            GreenSwitchConfiguration result = service.getConfigurationByType("notfound", GreenSwitchConfiguration.class);
            assertNull(result);
            verify(daoMock).getConfiguration(anyString(), isNull());
        }
    }

    @Test
    void testGetConfigurationPersonalByType_NotFound() {
        String anyKeyContext = nextObject(String.class);

        doReturn(Optional.empty())
                .when(daoMock).getConfiguration(anyString(), any());

        try (MockedStatic<GreenIdentityHolder> mockedHolder = mockStatic(GreenIdentityHolder.class)) {
            GreenIdentityProvider providerMock = mock(GreenIdentityProvider.class);
            mockedHolder.when(GreenIdentityHolder::get).thenReturn(providerMock);
            when(providerMock.getKeyContext()).thenReturn(anyKeyContext);

            GreenSwitchConfiguration result = service.getConfigurationByType("notfound", GreenSwitchConfiguration.class);
            assertNull(result);
            verify(daoMock).getConfiguration(anyString(), eq(anyKeyContext));
        }
    }

    @Test
    void testGetAllConfigurations() {
        GreenConfiguration conf1 = GreenConfiguration.builder()
                .configurations("{\"configType\":\"" + GreenSwitchConfiguration.class.getName() + "\"}")
                .build();
        GreenConfiguration conf2 = GreenConfiguration.builder()
                .configurations("{\"configType\":\"" + GreenAdjustableNumberConfiguration.class.getName() + "\"}")
                .build();

        doReturn(List.of(conf1, conf2))
                .when(daoMock).getAllConfigurations();

        List<GreenDefaultConfiguration> results = service.getAllConfigurations();

        assertEquals(2, results.size());
        assertTrue(results.get(0) instanceof GreenSwitchConfiguration);
        assertTrue(results.get(1) instanceof GreenAdjustableNumberConfiguration);
    }
}
