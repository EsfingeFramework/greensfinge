package br.com.ita.greenframework.configuration.facade;

import br.com.ita.greenframework.dto.project.GreenMetric;
import br.com.ita.greenframework.service.GreenMetricService;

import java.util.List;

public class GreenMetricFacade {

    private final GreenMetricService metricService = new GreenMetricService();

    public List<GreenMetric> getSavedEnergy() {
        return metricService.getSavedEnergy();
    }

}
