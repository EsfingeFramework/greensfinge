package br.com.ita.greenframework.dao.contract;

import br.com.ita.greenframework.dto.project.GreenMetric;

import java.util.List;

public interface GreenMetricDao {

    GreenMetric findById(String key);

    void save(GreenMetric metric);

    List<GreenMetric> getSavedEnergy();
}
