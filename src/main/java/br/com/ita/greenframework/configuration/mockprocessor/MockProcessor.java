package br.com.ita.greenframework.configuration.mockprocessor;

import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class MockProcessor {

    public abstract void process(Field field, Method method, ContainerField containerField);
}
