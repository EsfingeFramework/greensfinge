package net.sf.esfinge.greenframework.core.configuration.facade;

import net.sf.esfinge.greenframework.core.dto.project.GreenMetricResponse;
import net.sf.esfinge.greenframework.core.mapper.GreenMetricMapper;
import net.sf.esfinge.greenframework.core.service.GreenMetricService;

import java.util.ArrayList;

public class GreenMetricFacade {

    private final GreenMetricService metricService = new GreenMetricService();

    public GreenMetricResponse getSavedEnergy(boolean showTrace) {
        GreenMetricResponse response = GreenMetricMapper.INSTANCE.toTotal(metricService.getSavedEnergy());
        if(!showTrace) {
            response.getMethods().forEach(method -> method.setTraces(new ArrayList<>()));
        }
        return response;
    }

}
