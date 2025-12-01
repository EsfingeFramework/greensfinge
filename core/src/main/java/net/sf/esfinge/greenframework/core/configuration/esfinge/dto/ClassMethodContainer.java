package net.sf.esfinge.greenframework.core.configuration.esfinge.dto;

import lombok.Getter;
import lombok.Setter;
import net.sf.esfinge.greenframework.core.annotation.GreenConfigAnnotation;
import net.sf.esfinge.metadata.annotation.container.AllMethodsWith;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

import java.util.List;

@Getter
@Setter
@ContainerFor(ContainerTarget.TYPE)
public class ClassMethodContainer {

    @AllMethodsWith(GreenConfigAnnotation.class)
    private List<ContainerMethod> methods;

    @ReflectionReference
    private Class<?> clazz;
}
