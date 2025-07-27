package net.sf.esfinge.greenframework.core.dao.contract;

import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;

import java.util.List;
import java.util.Optional;

public interface GreenCustomMockDao {

    Optional<GreenCustomMockConfiguration> findByKey(String key);

    void insertGeneralConfiguration(GreenCustomMockConfiguration mockConfiguration);

    void updateGeneralConfiguration(GreenCustomMockConfiguration mockConfiguration);

    List<GreenCustomMockConfiguration> getAllConfigurations();

    GreenCustomMockConfiguration findByReturnType(String returnType);
}
