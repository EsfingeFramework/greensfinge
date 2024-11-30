package br.com.ita.greenframework.configuration.esfinge.processor;

import br.com.ita.greenframework.annotation.GreenDefault;
import br.com.ita.greenframework.annotation.GreenNumber;
import br.com.ita.greenframework.annotation.GreenOptional;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GreenReadProcessor {

    protected Annotation getGreenAnnotation(AnnotatedElement elementWithMetadata) {
        scanAllGreenAnnotations();
        return Arrays.stream(elementWithMetadata.getDeclaredAnnotations())
                .filter(e -> e.annotationType().equals(GreenOptional.class) || e.annotationType().equals(GreenNumber.class))
                .findFirst()
                .orElse(null);
    }

    private List<String> scanAllGreenAnnotations() {

        List<String> annotations = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(GreenDefault.class.getPackage().getName())
                .scan()) {

            annotations.add(scanResult.getAllClasses().toString());

        }

        return annotations;
    }
}
