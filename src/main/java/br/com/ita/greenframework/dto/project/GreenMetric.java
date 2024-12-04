package br.com.ita.greenframework.dto.project;

import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class GreenMetric {

    private String method;
    private ContainerField containerField;
    private Double metricSavedValue;
    private int countCalled = 0;
    private LocalDateTime measuredTime;

    @ToString.Include
    public Double getSavedValue() {
        return countCalled * metricSavedValue;
    }
}
