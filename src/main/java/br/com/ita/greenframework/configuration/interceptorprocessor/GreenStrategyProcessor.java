package br.com.ita.greenframework.configuration.interceptorprocessor;

import br.com.ita.greenframework.GreenException;
import br.com.ita.greenframework.annotation.GreenDefault;
import br.com.ita.greenframework.configuration.GreenThreadLocal;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class GreenStrategyProcessor {

    private static GreenOptionalProcessor instance;
    private static final Map<String, GreenStrategyProcessor> processorTypes = new HashMap<>();

    protected void populateData() {
        processorTypes.put("GreenOptional", new GreenOptionalProcessor());
        processorTypes.put("GreenNumber", new GreenNumberProcessor());
    }

    public GreenStrategyProcessor getProcessor(String nameGreenAnnotation) {
        return Optional.of(processorTypes)
                .filter(typeProcessor -> typeProcessor.containsKey(nameGreenAnnotation))
                .map(e -> e.get(nameGreenAnnotation))
                .orElseThrow(() -> new GreenException(String.format("Green annotation '%s' not found", nameGreenAnnotation)));
    }

    public abstract void process(Field field, ContainerField containerField, Object target);

    public static GreenOptionalProcessor getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (GreenStrategyProcessor.class) {
                if (Objects.isNull(instance)) {
                    instance = new GreenOptionalProcessor();
                    instance.populateData();
                }
            }
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    protected <T> T getThreadLocalConfiguration(GreenDefault configurationClass) {
        return Optional.ofNullable(configurationClass)
                .map(e-> (T) GreenThreadLocal.getValue(e.configurationKey()))
                .orElse(null);
    }

    protected void setReflectionValue(Field field, Object target, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(target, value);
    }

}
