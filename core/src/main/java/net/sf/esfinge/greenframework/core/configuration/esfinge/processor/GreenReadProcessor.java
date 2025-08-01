package net.sf.esfinge.greenframework.core.configuration.esfinge.processor;

import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GreenReadProcessor {

    protected List<Annotation> getAllGreenAnnotations(AnnotatedElement elementWithMetadata) {
        List<String> greenAnnotations = scanAllGreenAnnotations();
        return Arrays.stream(elementWithMetadata.getDeclaredAnnotations())
                .filter(e -> greenAnnotations.contains(e.annotationType().getName()))
                .collect(Collectors.toList());
    }

    private List<String> scanAllGreenAnnotations() {
        List<String> annotations;
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(GreenConfigKey.class.getPackage().getName())
                .scan()) {

            annotations = new ArrayList<>(scanResult.getAllClasses().getNames());

        }

        return annotations;
    }
}
