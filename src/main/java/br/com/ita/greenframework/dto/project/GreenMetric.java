package br.com.ita.greenframework.dto.project;

import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GreenMetric {

    private String method;
    private ContainerField containerField;
    private br.com.ita.greenframework.annotation.GreenMetric greenMetricAnnotation;
    private int countCalled = 0;

    @ToString.Include
    public Double getSavedValue() {
        return countCalled * greenMetricAnnotation.metricSavedValue();
    }
}
