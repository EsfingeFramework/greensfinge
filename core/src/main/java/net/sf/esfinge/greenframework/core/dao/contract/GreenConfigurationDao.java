package net.sf.esfinge.greenframework.core.dao.contract;

import net.sf.esfinge.greenframework.core.dto.annotation.GreenDefaultConfiguration;
import net.sf.esfinge.greenframework.core.entity.GreenConfiguration;

import java.util.List;
import java.util.Optional;

public interface GreenConfigurationDao {

    Optional<GreenConfiguration> getConfiguration(String configurationKey, String contextKey);

    List<GreenConfiguration> getAllConfigurations();

    void updateGeneralConfiguration(GreenDefaultConfiguration config);

    void insertGeneralConfiguration(GreenDefaultConfiguration config);

    Optional<GreenConfiguration> findGeneralConfiguration(String configurationKey);

    Optional<GreenConfiguration> findPersonalConfiguration(String configurationKey, String keyContext);

    void insertPersonalConfiguration(GreenDefaultConfiguration greenConfiguration);

    void updatePersonalConfiguration(GreenDefaultConfiguration greenConfiguration);
}
