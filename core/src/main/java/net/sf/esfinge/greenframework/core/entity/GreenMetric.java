package net.sf.esfinge.greenframework.core.entity;

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
    private Double metricSavedValue;
    private int countCalled = 0;
    private LocalDateTime beginMeasuredTime;
    private LocalDateTime endMeasuredTime;

}
