package br.com.ita.greenframework.configurations.esfinge.dto;

import br.com.ita.greenframework.configurations.esfinge.annotation.GreenReadAnnotation;
import br.com.ita.greenframework.configurations.esfinge.annotation.GreenReadAttributesAnnotation;
import br.com.ita.greenframework.configurations.esfinge.annotation.GreenReadFieldClassAnnotation;
import lombok.Getter;
import lombok.Setter;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.container.ContainerTarget;

import java.util.Map;

@Getter
@Setter
@ContainerFor(ContainerTarget.FIELDS)
public class ContainerField {

    @ElementName
    private String attributeName;

    @GreenReadAnnotation
    private boolean hasGreenAnnotation;

    @GreenReadFieldClassAnnotation
    private String annotationField;

    @GreenReadAttributesAnnotation
    private Map<String, Object> annotationValue;

}
