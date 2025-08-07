package net.sf.esfinge.greenframework.core.dto.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
public class GreenMetricResponseTrace {

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
