package net.sf.esfinge.greenframework.core.dao.contract;

import net.sf.esfinge.greenframework.core.dto.project.GreenMetric;

import java.util.List;

public interface GreenMetricDao {

    GreenMetric findById(String key);

    void save(GreenMetric metric);

    List<GreenMetric> getSavedEnergy();
}
