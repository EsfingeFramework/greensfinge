package net.sf.esfinge.greenframework.core.dao.memory;

import net.sf.esfinge.greenframework.core.dao.contract.GreenMetricDao;
import net.sf.esfinge.greenframework.core.dto.project.request.GreenMetricFilterRequest;
import net.sf.esfinge.greenframework.core.entity.GreenMetric;
import net.sf.esfinge.greenframework.core.entity.GreenMetricTrace;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
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
    public List<GreenMetric> getSavedEnergy(GreenMetricFilterRequest filter) {
        List<GreenMetric> metrics = new ArrayList<>(storage.get(keyName).values());

        if (!filter.isShowTrace()) {
            return metrics.stream().map(original -> {
                GreenMetric copy = GreenMetric.builder().build();
                try {
                    BeanUtils.copyProperties(copy, original);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    // Nothing to do
                }

                List<GreenMetricTrace> traces = original.getTraces();
                copy.setTraces(traces == null ? new ArrayList<>() : new ArrayList<>(traces));
                return copy;
            }).toList();
        }

        return metrics;
    }

}
