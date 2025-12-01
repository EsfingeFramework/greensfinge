package net.sf.esfinge.greenframework.core.entity;

import lombok.*;
import net.sf.esfinge.greenframework.core.entity.enuns.ScanMappingType;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GreenScanMapping {

    private ScanMappingType type;
    private String className;
    private String simpleClassName;
    private String name;
    private String fieldType;
    private List<GreenScanMappingConfiguration> greenScanMappingConfiguration;

}
