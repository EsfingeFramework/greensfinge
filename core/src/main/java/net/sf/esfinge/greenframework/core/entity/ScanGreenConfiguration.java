package net.sf.esfinge.greenframework.core.entity;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScanGreenConfiguration {

    private String className;
    private String fieldName;
    private String annotation;
    private Map<String, Object> configurationValues;

}
