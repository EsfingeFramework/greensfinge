package br.com.ita.greenframework.dao.memory;

import br.com.ita.greenframework.dto.project.GreenMetric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreenMetricDao extends GreenMemoryDao<Map<String, GreenMetric>> {

    private final String keyName = GreenMetricDao.class.getName();

    public GreenMetricDao () {
        storage.put(keyName, new HashMap<>());
        listStorage.put(keyName, new ArrayList<>());
    }

    public GreenMetric findById(String key) {
        return storage.get(keyName).get(key);
    }

    public void save(GreenMetric metric) {
        Map<String, GreenMetric> metrics = storage.get(keyName);

        metrics.put(metric.getMethod(), metric);
        storage.put(keyName, metrics);
    }

    public List<GreenMetric> getSavedEnergy() {
        return new ArrayList<>(storage.get(keyName).values());
    }
}
