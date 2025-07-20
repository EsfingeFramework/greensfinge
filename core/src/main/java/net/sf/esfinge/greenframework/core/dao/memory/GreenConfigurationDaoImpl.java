package net.sf.esfinge.greenframework.core.dao.memory;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.sf.esfinge.greenframework.core.dao.contract.GreenConfigurationDao;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenDefaultConfiguration;
import net.sf.esfinge.greenframework.core.dto.project.GreenConfiguration;

public class GreenConfigurationDaoImpl extends GreenMemoryDaoImpl<GreenConfiguration> implements GreenConfigurationDao {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public void setGeneralConfiguration(String configurationKey, GreenDefaultConfiguration config) {
        storage.put(configurationKey, GreenConfiguration.builder()
                .key(configurationKey)
                .configurations(objectMapper.writeValueAsString(config.toMap()))
                .build());
    }

    @Override
    public GreenConfiguration getConfiguration(String value) {
        return storage.get(value);
    }
}
