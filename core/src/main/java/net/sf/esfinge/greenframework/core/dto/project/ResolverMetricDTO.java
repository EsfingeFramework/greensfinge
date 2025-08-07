package net.sf.esfinge.greenframework.core.dto.project;

import lombok.Getter;
import lombok.Setter;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;

@Getter
@Setter
public class ResolverMetricDTO {

    private Double savedValue;
    private String key;
    private Boolean realCall;
    private Long begin;
    private Long end;
    private GreenSwitchConfiguration configuration;
    private GreenCustomMockConfiguration mockConfiguration;
}
