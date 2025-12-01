package net.sf.esfinge.greenframework.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GreenScanMappingConfiguration {

    private String annotation;
    private Object value;
}
