package net.sf.esfinge.greenframework.core.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.greenframework.core.dao.GreenFactoryDao;
import net.sf.esfinge.greenframework.core.dao.contract.GreenConfigurationDao;
import net.sf.esfinge.greenframework.core.dao.memory.GreenConfigurationDaoImpl;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenDefaultConfiguration;
import net.sf.esfinge.greenframework.core.dto.project.GreenConfiguration;

import java.util.Map;
import java.util.Objects;

@Slf4j
public class GreenConfigurationService {

    private final GreenConfigurationDao configurationDao = GreenFactoryDao.getInstance().create(GreenConfigurationDaoImpl.class);

    public void setGeneralConfiguration(String configurationKey, GreenDefaultConfiguration config) {
        configurationDao.setGeneralConfiguration(configurationKey, config);
    }

    public GreenConfiguration getConfiguration(String value) {
        return configurationDao.getConfiguration(value);
    }

    @SneakyThrows
    public <T extends GreenDefaultConfiguration> T getConfigurationByType(String value, Class<T> greenConfigClass) {
        GreenConfiguration configuration = configurationDao.getConfiguration(value);
        T configInstance = null;

        if(Objects.nonNull(configuration)) {
            configInstance = greenConfigClass.getDeclaredConstructor().newInstance();
            configInstance.setConfigurationKey(value);
            Map<String, Object> hashMap = new ObjectMapper().readValue(
                    configuration.getConfigurations(), new TypeReference<Map<String, Object>>() {} );
            configInstance.toObject(hashMap);
        }

        return configInstance;
    }
}
