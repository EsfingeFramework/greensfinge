package net.sf.esfinge.greenframework.core.configuration.esfinge.dto;

import net.sf.esfinge.greenframework.core.configuration.esfinge.annotation.GreenReadAttributesAnnotation;
import net.sf.esfinge.greenframework.core.configuration.esfinge.annotation.GreenReadFieldClassAnnotation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.container.ContainerTarget;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ContainerFor(ContainerTarget.FIELDS)
@ToString
public class ContainerField {

    @ElementName
    private String attributeName;

    @GreenReadFieldClassAnnotation
    private List<Class<?>> annotationField;

    @GreenReadAttributesAnnotation
    private Map<String, Object> annotationValue;

}
