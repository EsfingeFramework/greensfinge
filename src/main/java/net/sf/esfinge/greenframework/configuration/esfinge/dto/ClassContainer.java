package net.sf.esfinge.greenframework.configuration.esfinge.dto;

import net.sf.esfinge.greenframework.annotation.GreenConfigAnnotation;
import lombok.Getter;
import lombok.Setter;
import net.sf.esfinge.metadata.annotation.container.AllFieldsWith;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

import java.util.List;

@Getter
@Setter
@ContainerFor(ContainerTarget.TYPE)
public class ClassContainer {

    @AllFieldsWith(GreenConfigAnnotation.class)
    private List<ContainerField> fields;

    @ReflectionReference
    private Class<?> clazz;

}
