package net.sf.esfinge.greenframework.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;

@Getter
@Setter
@Builder
public class GreenMetricTrace {

    private Long begin;
    private Long end;
    private Boolean realCall;
    private Double metricSavedValue;
    private GreenSwitchConfiguration configuration;
    private GreenCustomMockConfiguration customMockConfiguration;

    public Long getDuration() {
        if(begin.equals(0L) || end.equals(0L)) return 0L;
        return end - begin;
    }
}
