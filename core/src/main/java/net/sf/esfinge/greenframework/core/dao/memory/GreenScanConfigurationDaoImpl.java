package net.sf.esfinge.greenframework.core.dao.memory;

import net.sf.esfinge.greenframework.core.dao.contract.GreenScanConfigurationDao;
import net.sf.esfinge.greenframework.core.entity.ScanGreenConfiguration;

import java.util.ArrayList;
import java.util.List;

public class GreenScanConfigurationDaoImpl extends GreenMemoryDaoImpl<ScanGreenConfiguration> implements GreenScanConfigurationDao {

    private final String keyName = GreenScanConfigurationDaoImpl.class.getName();

    public GreenScanConfigurationDaoImpl() {
        listStorage.put(keyName, new ArrayList<>());
    }

    public List<ScanGreenConfiguration> findConfigurations() {
        return listStorage.get(keyName);
    }

    public void save(List<ScanGreenConfiguration> configurations) {
        listStorage.put(keyName, configurations);
    }

}
