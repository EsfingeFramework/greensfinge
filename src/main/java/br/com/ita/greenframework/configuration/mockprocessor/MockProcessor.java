package br.com.ita.greenframework.configuration.mockprocessor;

import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;

import java.lang.reflect.Method;

public abstract class MockProcessor {

    public abstract void process(Method method, ContainerField containerField);
}
