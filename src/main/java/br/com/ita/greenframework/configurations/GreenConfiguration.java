package br.com.ita.greenframework.configurations;

import br.com.ita.greenframework.annotations.GreenConfigAnnotation;
import br.com.ita.greenframework.annotations.GreenDefault;
import br.com.ita.greenframework.configurations.esfinge.dto.ClassContainer;
import br.com.ita.greenframework.configurations.esfinge.dto.ContainerField;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import net.sf.esfinge.metadata.AnnotationReader;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class GreenConfiguration {

    public List<br.com.ita.greenframework.dto.GreenConfiguration> getConfigurationsInProject() {
        List<br.com.ita.greenframework.dto.GreenConfiguration> configs = new ArrayList<>();
        List<Class<Annotation>> classesAnnotations = scanAllAnnotations();

        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(GreenDefault.class.getPackage().getName())
                .scan()) {

            for (Class<Annotation> classAnnotation : classesAnnotations) {
                searchForAnnotation(scanResult, classAnnotation, configs);
            }

        }

        return configs;
    }

    private void searchForAnnotation(ScanResult scanResult, Class<Annotation> classAnnotation, List<br.com.ita.greenframework.dto.GreenConfiguration> configs) {
        scanResult.getAllClasses().forEach(classInfo -> {
            try {
                Class<?> clazz = Class.forName(classInfo.getName());

                AnnotationReader reader = new AnnotationReader();
                ClassContainer containerField = reader.readingAnnotationsTo(clazz, ClassContainer.class);

                if(!containerField.getFields().isEmpty()) {
                    for (ContainerField field : containerField.getFields()) {
                        if(field.isHasGreenAnnotation()) {
                            System.out.println(field);
                            configs.add(new br.com.ita.greenframework.dto.GreenConfiguration(clazz.getName(), field.getAttributeName(), classAnnotation.getName(), field.getAnnotationValue()));
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
