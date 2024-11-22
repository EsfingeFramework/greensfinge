package br.com.ita.greenframework.dao.memory;

import br.com.ita.greenframework.dto.project.GreenConfiguration;

import java.util.ArrayList;
import java.util.List;

public class GreenConfigurationDao extends GreenMemoryDao<GreenConfiguration> {

    private final String keyName = GreenConfigurationDao.class.getName();

    public GreenConfigurationDao () {
        listStorage.put(keyName, new ArrayList<>());
    }

    public List<GreenConfiguration> findConfigurations() {
        return listStorage.get(keyName);
    }

    public void save(List<GreenConfiguration> configurations) {
        listStorage.put(keyName, configurations);
    }
}
