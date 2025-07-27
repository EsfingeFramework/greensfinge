package net.sf.esfinge.greenframework.spring.starter.green.metric;

import net.sf.esfinge.greenframework.core.configuration.facade.GreenMetricFacade;
import net.sf.esfinge.greenframework.core.dto.project.GreenMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/metric")
public class MetricController {

    @Autowired
    private GreenMetricFacade greenMetricFacade;

    @GetMapping
    public List<GreenMetric> findAllMetric() {
        return greenMetricFacade.getSavedEnergy();
    }
}
