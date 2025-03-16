package net.sf.esfinge.greenframework.configuration.interceptorprocessor;

import net.sf.esfinge.greenframework.annotation.GreenAdjustableNumber;
import net.sf.esfinge.greenframework.annotation.GreenSwitch;
import net.sf.esfinge.greenframework.configuration.esfinge.dto.ContainerField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.util.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class GreenStrategyProcessor {

    private static GreenOptionalProcessor instance;
    private static final Map<Class<?>, GreenStrategyProcessor> processorTypes = new HashMap<>();

    protected void populateData() {
        processorTypes.put(GreenSwitch.class, new GreenOptionalProcessor());
        processorTypes.put(GreenAdjustableNumber.class, new GreenNumberProcessor());
    }

    public GreenStrategyProcessor getProcessor(Class<?> nameGreenAnnotation) {
        return Optional.of(processorTypes)
                .filter(typeProcessor -> typeProcessor.containsKey(nameGreenAnnotation))
                .map(e -> e.get(nameGreenAnnotation))
                .orElse(null);
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

    protected void setReflectionValue(Field field, Object target, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(target, value);
    }

}
