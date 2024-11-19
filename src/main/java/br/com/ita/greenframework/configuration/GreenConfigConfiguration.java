package br.com.ita.greenframework.configuration;

import br.com.ita.greenframework.annotation.GreenConfigAnnotation;
import br.com.ita.greenframework.configuration.esfinge.dto.ClassContainer;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.dto.GreenConfiguration;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import net.sf.esfinge.metadata.AnnotationReader;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class GreenConfigConfiguration {

    public List<GreenConfiguration> getConfigurationsInProject() {
        List<GreenConfiguration> configs = new ArrayList<>();
        List<Class<Annotation>> classesAnnotations = scanAllAnnotations();

        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(GreenEnvironment.getPackage())
                .scan()) {

                searchForAnnotation(scanResult, classesAnnotations, configs);
        }

        return configs;
    }

    private void searchForAnnotation(ScanResult scanResult, List<Class<Annotation>> classAnnotations, List<GreenConfiguration> configs) {
        scanResult.getAllClasses().forEach(classInfo -> {
            try {
                Class<?> clazz = Class.forName(classInfo.getName());

                AnnotationReader reader = new AnnotationReader();
                ClassContainer containerField = reader.readingAnnotationsTo(clazz, ClassContainer.class);

                if(!containerField.getFields().isEmpty()) {
                    for (ContainerField field : containerField.getFields()) {
                        if(field.isHasGreenAnnotation()) {
                            configs.add(GreenConfiguration.builder()
                                    .className(clazz.getName())
                                    .fieldName(field.getAttributeName())
                                    .annotation(field.getAnnotationField())
                                    .configurationValues(field.getAnnotationValue())
                                    .build());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private List<Class<Annotation>> scanAllAnnotations() {
        List<Class<Annotation>> listAnnotations = new ArrayList<>();

        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(GreenConfigAnnotation.class.getPackage().getName())
                .scan()) {

            scanResult.getAllClasses().forEach(classInfo -> {
                try {
                    Class<?> classAnnotation = Class.forName(classInfo.getName());
                    if(classAnnotation.isAnnotationPresent(GreenConfigAnnotation.class)) {
                        listAnnotations.add((Class<Annotation>) classAnnotation);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        return listAnnotations;
    }
}
