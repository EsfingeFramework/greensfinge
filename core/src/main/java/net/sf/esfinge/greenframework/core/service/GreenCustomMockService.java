package net.sf.esfinge.greenframework.core.service;

import lombok.Getter;
import net.sf.esfinge.greenframework.core.dao.GreenFactoryDao;
import net.sf.esfinge.greenframework.core.dao.contract.GreenCustomMockDao;
import net.sf.esfinge.greenframework.core.dao.memory.GreenCustomMockDaoImpl;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;

import java.util.List;
import java.util.Optional;

@Getter
public class GreenCustomMockService {

    private final GreenCustomMockDao customMockDao = GreenFactoryDao.getInstance().create(GreenCustomMockDaoImpl.class);

    public void createCustomMock(GreenCustomMockConfiguration mockConfiguration) {
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
}
