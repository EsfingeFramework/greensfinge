package net.sf.esfinge.greenframework.core.configuration.facade;

import net.sf.esfinge.greenframework.core.dto.project.request.GreenMetricFilterRequest;
import net.sf.esfinge.greenframework.core.dto.project.response.GreenMetricResponse;
import net.sf.esfinge.greenframework.core.mapper.GreenMetricMapper;
import net.sf.esfinge.greenframework.core.service.GreenMetricService;

public class GreenMetricFacade {

    private final GreenMetricService metricService = new GreenMetricService();

    public GreenMetricResponse getSavedEnergy(GreenMetricFilterRequest filter) {
        return GreenMetricMapper.INSTANCE.toTotal(metricService.getSavedEnergy(filter));
    }

}
