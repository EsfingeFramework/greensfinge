package br.com.ita.greenframework.configuration;

import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.dto.GreenOptionalConfiguration;
import br.com.ita.greenframework.util.GreenConstant;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@Slf4j
public class GreenGenericMocker {

    private final GreenThreadLocal greenThreadLocal = new GreenThreadLocal();
    private final ContainerField containerField;

    public GreenGenericMocker(ContainerField containerField) {
        this.containerField = containerField;
    }

    @RuntimeType
    public Object intercept(@This Object target, @AllArguments Object[] args, @Origin Method method, @SuperCall Callable<?> zuper) {
        log.debug("Intercepted: {} method: {} ", method.getDeclaringClass(), method.getName());

        if (String.class.equals(method.getReturnType())) {
            return getStrMockValue();
        } else if (Integer.class.equals(method.getReturnType())) {
            return getIntMockValue();
        } else if (method.getReturnType().equals(Void.TYPE)) {
            return null;
        }
        return null;
    }

    private Integer getIntMockValue() {
        String configurationKey = (String) containerField.getAnnotationValue().get("configurationKey");
        GreenOptionalConfiguration configuration = (GreenOptionalConfiguration)greenThreadLocal.getValue(configurationKey);

        if(GreenConstant.INT_DEFAULT_VALUE == (int)containerField.getAnnotationValue().get("numberDefaultValue")) {
            return configuration.getNumberDefaultValue();
        } else {
            return (Integer)containerField.getAnnotationValue().get("numberDefaultValue");
        }
    }

    private String getStrMockValue() {
        String configurationKey = (String) containerField.getAnnotationValue().get("configurationKey");
        GreenOptionalConfiguration configuration = (GreenOptionalConfiguration)greenThreadLocal.getValue(configurationKey);

        if(GreenConstant.STR_DEFAULT_VALUE.equals(containerField.getAnnotationValue().get("strDefaultValue"))) {
            return configuration.getStrDefaultValue();
        } else {
            return (String)containerField.getAnnotationValue().get("strDefaultValue");
        }
    }
}