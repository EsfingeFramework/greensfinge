package net.sf.esfinge.greenframework.core.service;

import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.greenframework.core.dao.GreenFactoryDao;
import net.sf.esfinge.greenframework.core.dao.contract.GreenMetricDao;
import net.sf.esfinge.greenframework.core.dao.memory.GreenMetricDaoImpl;
import net.sf.esfinge.greenframework.core.dto.project.ResolverMetricDTO;
import net.sf.esfinge.greenframework.core.entity.GreenMetric;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class GreenMetricService {

    private final GreenMetricDao greenMetricDao = GreenFactoryDao.getInstance().create(GreenMetricDaoImpl.class);

    public void save(ResolverMetricDTO dto) {
        processValues(dto);
        GreenMetric metric = greenMetricDao.findById(dto.getKey());

        if (Objects.isNull(metric)) {
            metric = GreenMetric.builder()
                    .method(dto.getKey())
                    .averageSavedValue(dto.getSavedValue())
                    .totalSavedValue(dto.getSavedValue())
                    .beginMeasuredTime(LocalDateTime.now())
                    .traces(new ArrayList<>())
                    .build();
            metric.createQtys(dto.getRealCall());
            metric.addTrace(dto);
        } else {
            metric.updateMetricValues(LocalDateTime.now(), dto);
        }
        greenMetricDao.save(metric);
    }

    private void processValues(ResolverMetricDTO dto) {
        if(dto.getRealCall()) {
            dto.setSavedValue(0.0);
        }
    }

    public List<GreenMetric> getSavedEnergy() {
        return greenMetricDao.getSavedEnergy();
    }

}
