package net.sf.esfinge.greenframework.core.service;

import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.greenframework.core.dao.GreenFactoryDao;
import net.sf.esfinge.greenframework.core.dao.contract.GreenMetricDao;
import net.sf.esfinge.greenframework.core.dao.memory.GreenMetricDaoImpl;
import net.sf.esfinge.greenframework.core.entity.GreenMetric;
import net.sf.esfinge.greenframework.core.mapper.MetricMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
public class GreenMetricService {

    private final GreenMetricDao greenMetricDao = GreenFactoryDao.getInstance().create(GreenMetricDaoImpl.class);

    public void save(Double savedValue, String key) {
        GreenMetric metric = greenMetricDao.findById(key);

        if (Objects.isNull(metric)) {
            metric = GreenMetric.builder()
                    .method(key)
                    .metricSavedValue(savedValue)
                    .countCalled(1)
                    .beginMeasuredTime(LocalDateTime.now())
                    .build();
        } else {
            metric.setEndMeasuredTime(LocalDateTime.now());
            metric.setCountCalled(metric.getCountCalled() + 1);
        }
        greenMetricDao.save(metric);
    }

    public List<GreenMetric> getSavedEnergy() {
        return greenMetricDao.getSavedEnergy();
    }
}
