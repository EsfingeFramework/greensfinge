package br.com.ita.greenframework.configurations;

import br.com.ita.greenframework.annotations.GreenConfigAnnotation;
import br.com.ita.greenframework.dto.GreenConfigurationDTO;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class GreenConfiguration {

    public List<GreenConfigurationDTO> getConfigurationsInProject() {
        List<GreenConfigurationDTO> configs = new ArrayList<>();
        List<Class<Annotation>> classesAnnotations = scanAllAnnotations();

        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages("br.com.ita.greenframework")
                .scan()) {

            for (Class<Annotation> classAnnotation : classesAnnotations) {
                searchForAnnotation(scanResult, classAnnotation, configs);
            }

        }

        return configs;
    }

    private void searchForAnnotation(ScanResult scanResult, Class<Annotation> classAnnotation, List<GreenConfigurationDTO> configs) {
        scanResult.getAllClasses().forEach(classInfo -> {
            try {
                Class<?> clazz = Class.forName(classInfo.getName());
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.isAnnotationPresent(classAnnotation)) {
                        Annotation annotation = field.getAnnotation(classAnnotation);
                        String configurationKey = (String) annotation.annotationType().getMethod("configurationKey").invoke(annotation);

                        configs.add(new GreenConfigurationDTO(clazz.getName(), field.getName(), classAnnotation.getName(), configurationKey));
                    }
                }
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
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
