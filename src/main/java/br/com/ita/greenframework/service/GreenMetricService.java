package br.com.ita.greenframework.service;

import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.dao.GreenFactoryDao;
import br.com.ita.greenframework.dao.contract.GreenMetricDao;
import br.com.ita.greenframework.dao.memory.GreenMetricDaoImpl;
import br.com.ita.greenframework.dto.project.GreenMetric;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
public class GreenMetricService {

    private final GreenMetricDao greenMetricDao = GreenFactoryDao.getInstance().create(GreenMetricDaoImpl.class);

    public void save(Double savedValue, String key, ContainerField containerField) {
        GreenMetric metric = greenMetricDao.findById(key);

        if (Objects.isNull(metric)) {
            metric = GreenMetric.builder().
                    method(key)
                    .metricSavedValue(savedValue)
                    .containerField(containerField)
                    .countCalled(1)
                    .measuredTime(LocalDateTime.now())
                    .build();
        } else {
            metric.setCountCalled(metric.getCountCalled() + 1);
        }
        greenMetricDao.save(metric);
    }

    public List<GreenMetric> getSavedEnergy() {
        return greenMetricDao.getSavedEnergy();
    }
}
