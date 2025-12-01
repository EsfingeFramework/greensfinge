package net.sf.esfinge.greenframework.core.dao.contract;

import net.sf.esfinge.greenframework.core.entity.GreenScanMapping;

import java.util.List;

public interface GreenScanConfigurationDao {

    List<GreenScanMapping> findConfigurations();

    void save(List<GreenScanMapping> configurations);

    void deleteAll();
}
