package net.sf.esfinge.greenframework.core.dto.project.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import net.sf.esfinge.greenframework.core.entity.GreenScanMappingConfiguration;
import net.sf.esfinge.greenframework.core.entity.enuns.ScanMappingType;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@JsonInclude(NON_NULL)
public class GreenScanMappingResponse {

    private ScanMappingType type;
    private String className;
    private String simpleClassName;
    private String name;
    private String fieldType;
    @JsonProperty("configuration")
    private List<GreenScanMappingConfiguration> greenScanMappingConfiguration;

}
