package br.com.ita.greenframework.dao.contract;

import br.com.ita.greenframework.dto.project.GreenConfiguration;

import java.util.List;

public interface GreenConfigurationDao {

    List<GreenConfiguration> findConfigurations();

    void save(List<GreenConfiguration> configurations);
}
