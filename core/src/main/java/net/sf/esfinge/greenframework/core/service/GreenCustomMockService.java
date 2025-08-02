package net.sf.esfinge.greenframework.core.service;

import lombok.Getter;
import lombok.SneakyThrows;
import net.sf.esfinge.greenframework.core.configuration.mockreturn.GreenCustomMockProvider;
import net.sf.esfinge.greenframework.core.dao.GreenFactoryDao;
import net.sf.esfinge.greenframework.core.dao.contract.GreenCustomMockDao;
import net.sf.esfinge.greenframework.core.dao.memory.GreenCustomMockDaoImpl;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;
import net.sf.esfinge.greenframework.core.entity.enuns.Messages;
import net.sf.esfinge.greenframework.core.exception.GreenBusinessException;
import net.sf.esfinge.greenframework.core.util.JsonUtil;

import java.util.List;
import java.util.Optional;

@Getter
public class GreenCustomMockService {

    private final GreenCustomMockDao customMockDao = GreenFactoryDao.getInstance().create(GreenCustomMockDaoImpl.class);

    public void createCustomMock(GreenCustomMockConfiguration mockConfiguration) {
        validateCustomMock(mockConfiguration);
        Optional<GreenCustomMockConfiguration> config  = customMockDao.findByKey(mockConfiguration.getKey());

        if(config.isEmpty()) {
            customMockDao.insertGeneralConfiguration(mockConfiguration);
        } else {
            customMockDao.updateGeneralConfiguration(mockConfiguration);
        }
    }

    public List<GreenCustomMockConfiguration> getAllConfigurations() {
        return customMockDao.getAllConfigurations();
    }

    public GreenCustomMockConfiguration findByReturnType(String returnType) {
        return customMockDao.findByReturnType(returnType);
    }

    @SneakyThrows
    private void validateCustomMock(GreenCustomMockConfiguration mockConfiguration) {
        if(!JsonUtil.isValid(mockConfiguration.getDefaultValue())) {
            throw new GreenBusinessException(Messages.ERROR_CUSTOM_MOCK_DEFAULT_VALUE, mockConfiguration.getDefaultValue());
        }

        isValidInstanceOfMockProvider(mockConfiguration.getCustomClass());
        isValidReturnTypeOfMockProvider(mockConfiguration, mockConfiguration.getReturnType());
    }

    @SneakyThrows
    private void isValidReturnTypeOfMockProvider(GreenCustomMockConfiguration mockConfiguration, String returnType) {
        GreenCustomMockProvider customMockProvider = (GreenCustomMockProvider) Class.forName(mockConfiguration.getCustomClass())
                .getDeclaredConstructor().newInstance();

        Object customMockObject = customMockProvider.processCustomMockReturn(mockConfiguration, mockConfiguration.toMap());

        Class<?> expectedType = Class.forName(returnType);

        boolean isValid = expectedType.isInstance(customMockObject);

        if(!isValid) {
            throw new GreenBusinessException(Messages.ERROR_CUSTOM_MOCK_RETURN_TYPE,
                    mockConfiguration.getReturnType(),
                    customMockProvider.getClass().getName(),
                    expectedType.getName());
        }
    }

    @SneakyThrows
    private void isValidInstanceOfMockProvider(String customClass) {
        Object customMockProvider = Class.forName(customClass)
                .getDeclaredConstructor().newInstance();

        boolean isValid = customMockProvider instanceof GreenCustomMockProvider;
        if(!isValid) {
            throw new GreenBusinessException(Messages.ERROR_CUSTOM_MOCK_NOT_INSTANCE, customMockProvider.getClass());
        }
    }
}
