package net.sf.esfinge.greenframework.configuration.facade;

import net.sf.esfinge.greenframework.dto.project.GreenMetric;
import net.sf.esfinge.greenframework.service.GreenMetricService;

import java.util.List;

public class GreenMetricFacade {

    private final GreenMetricService metricService = new GreenMetricService();

    public List<GreenMetric> getSavedEnergy() {
        return metricService.getSavedEnergy();
    }

}
