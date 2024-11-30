package br.com.ita.greenframework.configuration.esfinge.dto;

import br.com.ita.greenframework.configuration.esfinge.annotation.GreenReadAttributesAnnotation;
import br.com.ita.greenframework.configuration.esfinge.annotation.GreenReadFieldClassAnnotation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.container.ContainerTarget;

import java.util.Map;

@Getter
@Setter
@ContainerFor(ContainerTarget.FIELDS)
@ToString
public class ContainerField {

    @ElementName
    private String attributeName;

    @GreenReadFieldClassAnnotation
    private String annotationField;

    @GreenReadAttributesAnnotation
    private Map<String, Object> annotationValue;

}
