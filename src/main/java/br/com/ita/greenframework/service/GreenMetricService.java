package br.com.ita.greenframework.service;

import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.dao.GreenFactoryDao;
import br.com.ita.greenframework.dao.memory.GreenMetricDao;
import br.com.ita.greenframework.dto.project.GreenMetric;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

@Slf4j
public class GreenMetricService {

    private final GreenMetricDao greenMetricDao = GreenFactoryDao.getInstance().create(GreenMetricDao.class);

    public void save(Method method, ContainerField containerField) {
        br.com.ita.greenframework.annotation.GreenMetric annotation = method.getAnnotation(br.com.ita.greenframework.annotation.GreenMetric.class);

        if (Objects.nonNull(annotation)) {
            String key = String.format("%s#%s",method.getDeclaringClass().getName(), method.getName());
            GreenMetric metric = greenMetricDao.findById(key);

            if(Objects.isNull(metric)) {
                metric = new GreenMetric();
                metric.setMethod(key);
                metric.setGreenMetricAnnotation(annotation);
                metric.setContainerField(containerField);
                metric.setCountCalled(1);
            } else {
                metric.setCountCalled(metric.getCountCalled()+1);
            }
            greenMetricDao.save(metric);
        }
    }

    public List<GreenMetric> getSavedEnergy() {
        return greenMetricDao.getSavedEnergy();
    }
}
