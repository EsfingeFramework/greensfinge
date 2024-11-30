package br.com.ita.greenframework.configuration.mockprocessor;

import br.com.ita.greenframework.annotation.GreenDefaultReturn;
import br.com.ita.greenframework.configuration.GreenThreadLocal;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.dto.annotation.GreenOptionalConfiguration;
import br.com.ita.greenframework.util.GreenConstant;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
public class GreenReturnMockValue {

    private final ContainerField containerField;

    public GreenReturnMockValue(ContainerField containerField) {
        this.containerField = containerField;
    }

    public Object getReturnValue(Method method) {
        GreenDefaultReturn greenDefaultReturn = method.getAnnotation(GreenDefaultReturn.class);

        if(Objects.isNull(greenDefaultReturn)) {
            log.debug("The {}#{} method his mocked, but does not contain the @GreenDefaultValue annotation",
                    method.getDeclaringClass().getName(), method.getName());
        }

        return processReturnByType(method, greenDefaultReturn);
    }

    private Object processReturnByType(Method method, GreenDefaultReturn greenDefaultReturn) {

        if (String.class.equals(method.getReturnType())) {
            return getStrMockValue(greenDefaultReturn);
        } else if (Integer.class.equals(method.getReturnType())) {
            return getIntMockValue(greenDefaultReturn);
        } else if (method.getReturnType().equals(Void.TYPE)) {
            return null;
        } else if (Long.class.equals(method.getReturnType())) {
            return getIntMockValue(greenDefaultReturn).longValue();
        }
        return null;
    }

    private Integer getIntMockValue(GreenDefaultReturn greenDefaultReturn) {
        String configurationKey = (String) containerField.getAnnotationValue().get("configurationKey");
        GreenOptionalConfiguration configuration = GreenThreadLocal.getValue(configurationKey);

        if(Objects.isNull(greenDefaultReturn) || Objects.isNull(configuration)) {
            return (int) GreenConstant.DOUBLE_DEFAULT_VALUE;
        } else if(GreenConstant.DOUBLE_DEFAULT_VALUE == greenDefaultReturn.numberValue()) {
            return configuration.getNumberDefaultValue().intValue();
        } else {
            return (int) greenDefaultReturn.numberValue();
        }
    }

    private String getStrMockValue(GreenDefaultReturn greenDefaultReturn) {
        String configurationKey = (String) containerField.getAnnotationValue().get("configurationKey");
        GreenOptionalConfiguration configuration = GreenThreadLocal.getValue(configurationKey);

        if(Objects.nonNull(greenDefaultReturn) && !GreenConstant.STR_DEFAULT_VALUE.equals(greenDefaultReturn.strValue())) {
            return greenDefaultReturn.strValue();
        } else if(Objects.nonNull(configuration)) {
            return configuration.getStrDefaultValue();
        } else {
            return greenDefaultReturn.strValue();
        }
    }
}
