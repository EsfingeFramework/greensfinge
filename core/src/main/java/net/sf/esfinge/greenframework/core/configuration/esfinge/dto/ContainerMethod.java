package net.sf.esfinge.greenframework.core.configuration.esfinge.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.esfinge.greenframework.core.configuration.esfinge.annotation.GreenReadAttributesAnnotation;
import net.sf.esfinge.greenframework.core.configuration.esfinge.annotation.GreenReadFieldClassAnnotation;
import net.sf.esfinge.greenframework.core.configuration.esfinge.annotation.GreenReadMethodNameAnnotation;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ElementName;
import net.sf.esfinge.metadata.container.ContainerTarget;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@ContainerFor(ContainerTarget.METHODS)
public class ContainerMethod {

    @ElementName
    private String attributeName;

    @GreenReadMethodNameAnnotation
    private String methodName;

    @GreenReadFieldClassAnnotation
    private List<Class<?>> annotationField;

    @GreenReadAttributesAnnotation
    private Map<String, Object> annotationValue;

}
