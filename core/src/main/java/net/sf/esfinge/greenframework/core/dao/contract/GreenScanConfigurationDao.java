package net.sf.esfinge.greenframework.core.dao.contract;

import net.sf.esfinge.greenframework.core.dto.project.ScanGreenConfiguration;

import java.util.List;

public interface GreenScanConfigurationDao {

    List<ScanGreenConfiguration> findConfigurations();

    void save(List<ScanGreenConfiguration> configurations);
}
