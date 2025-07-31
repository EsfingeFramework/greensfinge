package net.sf.esfinge.greenframework.core.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.greenframework.core.dao.GreenFactoryDao;
import net.sf.esfinge.greenframework.core.dao.contract.GreenConfigurationDao;
import net.sf.esfinge.greenframework.core.dao.memory.GreenConfigurationDaoImpl;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenDefaultConfiguration;
import net.sf.esfinge.greenframework.core.entity.GreenConfiguration;
import net.sf.esfinge.greenframework.core.identity.GreenIdentityHolder;
import net.sf.esfinge.greenframework.core.identity.GreenIdentityProvider;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class GreenConfigurationService {

    private final GreenConfigurationDao configurationDao = GreenFactoryDao.getInstance().create(GreenConfigurationDaoImpl.class);

    public void setGeneralConfiguration(String configurationKey, GreenDefaultConfiguration greenConfiguration) {
        Optional<GreenConfiguration> config  = configurationDao.findGeneralConfiguration(configurationKey);
        if(config.isEmpty()) {
            configurationDao.insertGeneralConfiguration(greenConfiguration);
        } else {
            configurationDao.updateGeneralConfiguration(greenConfiguration);
        }
    }

    @SneakyThrows
    public <T extends GreenDefaultConfiguration> T getConfigurationByType(String configurationKey, Class<T> greenConfigClass) {
        GreenIdentityProvider greenIdentityProvider = GreenIdentityHolder.get();
        Optional<GreenConfiguration> configuration = configurationDao.getConfiguration(configurationKey, greenIdentityProvider.getKeyContext());
        return configuration.map(greenConfiguration -> createInstanceConfiguration(greenConfiguration, greenConfigClass))
                .orElse(null);

    }

    @SneakyThrows
    private <T extends GreenDefaultConfiguration> T createInstanceConfiguration(GreenConfiguration configuration, Class<T> greenConfigClass) {
        T configInstance = greenConfigClass.getDeclaredConstructor().newInstance();
        configInstance.setConfigurationKey(configuration.getKey());
        Map<String, Object> hashMap = new ObjectMapper().readValue(
                configuration.getConfigurations(), new TypeReference<Map<String, Object>>() {} );
        return (T) configInstance.toObject(hashMap);
    }


    public void setPersonalConfiguration(String configurationKey, GreenDefaultConfiguration greenConfiguration) {
        Optional<GreenConfiguration> config = configurationDao.findPersonalConfiguration(configurationKey, greenConfiguration.getKeyContext());
        if(config.isEmpty()) {
            configurationDao.insertPersonalConfiguration(greenConfiguration);
        } else {
            configurationDao.updatePersonalConfiguration(greenConfiguration);
        }
    }

    public List<GreenDefaultConfiguration> getAllConfigurations() {
        return configurationDao.getAllConfigurations().stream()
                .map(this::getConfigurationByType)
                .toList();
    }

    @SneakyThrows
    private GreenDefaultConfiguration getConfigurationByType(GreenConfiguration configuration) {
        Map<String, Object> hashMap = new ObjectMapper().readValue(
                configuration.getConfigurations(), new TypeReference<Map<String, Object>>() {} );
        GreenDefaultConfiguration greenConfigClass = Class.forName(hashMap.get("configType").toString())
                .asSubclass(GreenDefaultConfiguration.class)
                .getConstructor()
                .newInstance();
        return greenConfigClass.toObject(hashMap);
    }
}
