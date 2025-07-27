package net.sf.esfinge.greenframework.core.dao.memory;

import net.sf.esfinge.greenframework.core.dao.contract.GreenCustomMockDao;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;

import java.util.List;
import java.util.Optional;

public class GreenCustomMockDaoImpl extends GreenMemoryDaoImpl<GreenCustomMockConfiguration> implements GreenCustomMockDao {

    @Override
    public Optional<GreenCustomMockConfiguration> findByKey(String key) {
        return Optional.ofNullable(storage.get(key));
    }

    @Override
    public void insertGeneralConfiguration(GreenCustomMockConfiguration mockConfiguration) {
        storage.put(mockConfiguration.getKey(), mockConfiguration);
    }

    @Override
    public void updateGeneralConfiguration(GreenCustomMockConfiguration mockConfiguration) {
        storage.put(mockConfiguration.getKey(), mockConfiguration);
    }

    @Override
    public List<GreenCustomMockConfiguration> getAllConfigurations() {
        return storage.values().stream()
                .toList();
    }

    @Override
    public GreenCustomMockConfiguration findByReturnType(String returnType) {
        return storage.values().stream().
                filter(config -> config.getReturnType().equals(returnType))
                .findFirst()
                .orElse(null);
    }
}
