package br.com.ita.greenframework.configurations.esfinge.processor;

import br.com.ita.greenframework.annotations.GreenNumberConfig;
import br.com.ita.greenframework.annotations.GreenOptional;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

public abstract class GreenReadProcessor {

    protected Annotation getGreenAnnotation(AnnotatedElement elementWithMetadata) {
        return Arrays.stream(elementWithMetadata.getDeclaredAnnotations())
                .filter(e -> e.annotationType().equals(GreenOptional.class) || e.annotationType().equals(GreenNumberConfig.class))
                .findFirst()
                .orElse(null);
    }
}
