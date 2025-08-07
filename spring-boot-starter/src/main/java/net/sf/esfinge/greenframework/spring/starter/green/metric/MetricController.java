package net.sf.esfinge.greenframework.spring.starter.green.metric;

import net.sf.esfinge.greenframework.core.configuration.facade.GreenMetricFacade;
import net.sf.esfinge.greenframework.core.dto.project.GreenMetricResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/green/metrics")
public class MetricController {

    @Autowired
    private GreenMetricFacade greenMetricFacade;

    @GetMapping
    public GreenMetricResponse findAllMetric(@RequestParam(value = "showTrace", required = false) boolean showTrace) {
        return greenMetricFacade.getSavedEnergy(showTrace);
    }
}
