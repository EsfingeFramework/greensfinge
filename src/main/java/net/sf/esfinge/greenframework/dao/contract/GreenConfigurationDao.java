package net.sf.esfinge.greenframework.dao.contract;

import net.sf.esfinge.greenframework.dto.project.GreenConfiguration;

import java.util.List;

public interface GreenConfigurationDao {

    List<GreenConfiguration> findConfigurations();

    void save(List<GreenConfiguration> configurations);
}
