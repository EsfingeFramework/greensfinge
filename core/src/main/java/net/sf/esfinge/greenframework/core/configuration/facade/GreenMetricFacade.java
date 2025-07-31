package net.sf.esfinge.greenframework.core.configuration.facade;

import net.sf.esfinge.greenframework.core.dto.project.GreenMetricResponse;
import net.sf.esfinge.greenframework.core.mapper.GreenMetricMapper;
import net.sf.esfinge.greenframework.core.service.GreenMetricService;

import java.util.List;

public class GreenMetricFacade {

    private final GreenMetricService metricService = new GreenMetricService();

    public List<GreenMetricResponse> getSavedEnergy() {
        return GreenMetricMapper.INSTANCE.toResponse(metricService.getSavedEnergy());
    }

}
