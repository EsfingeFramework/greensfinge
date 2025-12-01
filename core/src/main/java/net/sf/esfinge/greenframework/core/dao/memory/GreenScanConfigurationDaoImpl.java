package net.sf.esfinge.greenframework.core.dao.memory;

import net.sf.esfinge.greenframework.core.dao.contract.GreenScanConfigurationDao;
import net.sf.esfinge.greenframework.core.entity.GreenScanMapping;

import java.util.ArrayList;
import java.util.List;

public class GreenScanConfigurationDaoImpl extends GreenMemoryDaoImpl<GreenScanMapping> implements GreenScanConfigurationDao {

    private final String keyName = GreenScanConfigurationDaoImpl.class.getName();

    public GreenScanConfigurationDaoImpl() {
        listStorage.put(keyName, new ArrayList<>());
    }

    public List<GreenScanMapping> findConfigurations() {
        return listStorage.get(keyName);
    }

    public void save(List<GreenScanMapping> configurations) {
        listStorage.put(keyName, configurations);
    }

    @Override
    public void deleteAll() {
        listStorage.get(keyName).clear();
    }

}
