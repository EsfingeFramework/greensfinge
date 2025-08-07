package net.sf.esfinge.greenframework.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.esfinge.greenframework.core.dto.project.ResolverMetricDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class GreenMetric {

    private String method;
    private Double averageSavedValue;
    private Double totalSavedValue;
    private Integer countCalled = 0;
    private Integer qtyRealCall = 0;
    private Integer qtyMockCall = 0;
    private LocalDateTime beginMeasuredTime;
    private Double timeSavedMillis;
    private Long realCallTotalDuration = 0L;
    private Long mockCallTotalDuration = 0L;
    private LocalDateTime endMeasuredTime;
    private List<GreenMetricTrace> traces;

    public void addTrace(ResolverMetricDTO dto) {
        GreenMetricTrace trace = GreenMetricTrace.builder()
                .begin(dto.getBegin())
                .end(dto.getEnd())
                .realCall(dto.getRealCall())
                .metricSavedValue(dto.getSavedValue())
                .configuration(dto.getConfiguration())
                .customMockConfiguration(dto.getMockConfiguration())
                .build();
        if(dto.getRealCall()) {
            this.realCallTotalDuration += trace.getDuration();
        } else {
            this.mockCallTotalDuration += trace.getDuration();
        }
        traces.add(trace);
    }

    public void updateMetricValues(LocalDateTime endMeasuredTime, ResolverMetricDTO dto) {
        this.endMeasuredTime = endMeasuredTime;
        this.totalSavedValue += dto.getSavedValue();

        if (dto.getSavedValue() != null) {
            this.averageSavedValue = ((this.averageSavedValue * this.countCalled) + dto.getSavedValue()) / (this.countCalled + 1);
        }
        this.countCalled++;
        if(dto.getRealCall()) {
            qtyRealCall++;
        } else {
            qtyMockCall++;
        }
        this.addTrace(dto);
    }

    public void createQtys(boolean realCall) {
        this.countCalled = 1;
        if(realCall) {
            this.qtyRealCall = 1;
            this.qtyMockCall = 0;
        } else {
            this.qtyRealCall = 0;
            this.qtyMockCall = 1;
        }
        this.realCallTotalDuration = 0L;
        this.mockCallTotalDuration = 0L;
    }
}
