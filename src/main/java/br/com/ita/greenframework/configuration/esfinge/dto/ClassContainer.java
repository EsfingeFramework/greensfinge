package br.com.ita.greenframework.configuration.esfinge.dto;

import lombok.Getter;
import lombok.Setter;
import net.sf.esfinge.metadata.annotation.container.ContainerFor;
import net.sf.esfinge.metadata.annotation.container.ProcessFields;
import net.sf.esfinge.metadata.annotation.container.ReflectionReference;
import net.sf.esfinge.metadata.container.ContainerTarget;

import java.util.List;

@Getter
@Setter
@ContainerFor(ContainerTarget.TYPE)
public class ClassContainer {

    @ProcessFields
    private List<ContainerField> fields;

    @ReflectionReference
    private Class<?> clazz;

}
