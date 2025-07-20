package net.sf.esfinge.greenframework.core.configuration.interceptorprocessor;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import net.sf.esfinge.greenframework.core.annotation.GreenAdjustableNumber;
import net.sf.esfinge.greenframework.core.annotation.GreenSwitch;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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

}
