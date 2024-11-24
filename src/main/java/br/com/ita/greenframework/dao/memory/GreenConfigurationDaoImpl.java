package br.com.ita.greenframework.dao.memory;

import br.com.ita.greenframework.dao.contract.GreenConfigurationDao;
import br.com.ita.greenframework.dto.project.GreenConfiguration;

import java.util.ArrayList;
import java.util.List;

public class GreenConfigurationDaoImpl extends GreenMemoryDaoImpl<GreenConfiguration> implements GreenConfigurationDao {

    private final String keyName = GreenConfigurationDaoImpl.class.getName();

    public GreenConfigurationDaoImpl() {
        listStorage.put(keyName, new ArrayList<>());
    }

    public List<GreenConfiguration> findConfigurations() {
        return listStorage.get(keyName);
    }

    public void save(List<GreenConfiguration> configurations) {
        listStorage.put(keyName, configurations);
    }
}
