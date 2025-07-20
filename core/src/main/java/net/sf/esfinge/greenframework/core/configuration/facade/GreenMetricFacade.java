package net.sf.esfinge.greenframework.core.configuration.facade;

import net.sf.esfinge.greenframework.core.dto.project.GreenMetric;
import net.sf.esfinge.greenframework.core.service.GreenMetricService;

import java.util.List;

public class GreenMetricFacade {

    private final GreenMetricService metricService = new GreenMetricService();

    public List<GreenMetric> getSavedEnergy() {
        return metricService.getSavedEnergy();
    }

}
