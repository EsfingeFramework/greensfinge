package br.com.ita.greenframework.dao.memory;

import br.com.ita.greenframework.dao.contract.GreenMetricDao;
import br.com.ita.greenframework.dto.project.GreenMetric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreenMetricDaoImpl extends GreenMemoryDaoImpl<Map<String, GreenMetric>> implements GreenMetricDao {

    private final String keyName = GreenMetricDaoImpl.class.getName();

    public GreenMetricDaoImpl() {
        storage.put(keyName, new HashMap<>());
    }

    @Override
    public GreenMetric findById(String key) {
        return storage.get(keyName).get(key);
    }

    @Override
    public void save(GreenMetric metric) {
        Map<String, GreenMetric> metrics = storage.get(keyName);

        metrics.put(metric.getMethod(), metric);
        storage.put(keyName, metrics);
    }

    @Override
    public List<GreenMetric> getSavedEnergy() {
        return new ArrayList<>(storage.get(keyName).values());
    }
}