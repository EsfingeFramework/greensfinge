package net.sf.esfinge.greenframework.core.service;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ClassFieldContainer;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ClassMethodContainer;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerMethod;
import net.sf.esfinge.greenframework.core.dao.GreenFactoryDao;
import net.sf.esfinge.greenframework.core.dao.contract.GreenScanConfigurationDao;
import net.sf.esfinge.greenframework.core.dao.memory.GreenScanConfigurationDaoImpl;
import net.sf.esfinge.greenframework.core.entity.GreenScanMapping;
import net.sf.esfinge.greenframework.core.entity.GreenScanMappingConfiguration;
import net.sf.esfinge.greenframework.core.entity.enuns.ScanMappingType;
import net.sf.esfinge.greenframework.core.util.GreenEnvironment;
import net.sf.esfinge.metadata.AnnotationReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class GreenScanMappingService {

    private final GreenScanConfigurationDao scanConfigurationDao = GreenFactoryDao.getInstance().create(GreenScanConfigurationDaoImpl.class);

    public List<GreenScanMapping> getConfigurationsInProject() {
        List<GreenScanMapping> configurations = scanConfigurationDao.findConfigurations();

        if (configurations.isEmpty()) {
            configurations = scanForConfigurations();
        }

        return configurations;
    }

    public List<GreenScanMapping> scanForConfigurations() {
        String scanPackage = GreenEnvironment.getDefaultPackage();
        log.info("Scanning package: {}", scanPackage);

        List<GreenScanMapping> configs = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(scanPackage)
                .scan()) {

            searchForAnnotation(scanResult, configs);
        }
        return configs;
    }

    private void searchForAnnotation(ScanResult scanResult, List<GreenScanMapping> configs) {
        scanResult.getAllClasses().forEach(classInfo -> {
            try {
                Class<?> clazz = Class.forName(classInfo.getName());

                processAnnotationsFields(clazz, configs);
                processAnnotationsMethods(clazz, configs);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        saveAll(configs);
    }

    private void processAnnotationsMethods(Class<?> clazz, List<GreenScanMapping> configs) throws Exception {
        AnnotationReader reader = new AnnotationReader();
        ClassMethodContainer classContainer = reader.readingAnnotationsTo(clazz, ClassMethodContainer.class);

        if (!classContainer.getMethods().isEmpty()) {
            for (ContainerMethod containerMethod : classContainer.getMethods()) {
                configs.add(GreenScanMapping.builder()
                        .className(clazz.getName())
                        .type(ScanMappingType.METHOD)
                        .simpleClassName(clazz.getSimpleName())
                        .name(containerMethod.getMethodName())
                        .greenScanMappingConfiguration(createScanMappingConfiguration(containerMethod.getAnnotationField(),
                                containerMethod.getAnnotationValue()))
                        .build());
            }
        }
    }

    private void processAnnotationsFields(Class<?> clazz, List<GreenScanMapping> configs) throws Exception {
        AnnotationReader reader = new AnnotationReader();
        ClassFieldContainer classFieldContainer = reader.readingAnnotationsTo(clazz, ClassFieldContainer.class);

        if (!classFieldContainer.getFields().isEmpty()) {
            for (ContainerField containerField : classFieldContainer.getFields()) {
                configs.add(GreenScanMapping.builder()
                        .className(clazz.getName())
                        .type(ScanMappingType.FIELD)
                        .simpleClassName(clazz.getSimpleName())
                        .name(containerField.getAttributeName())
                        .fieldType(containerField.getAttributeType())
                        .greenScanMappingConfiguration(createScanMappingConfiguration(containerField.getAnnotationField(),
                                containerField.getAnnotationValue()))
                        .build());
            }
        }
    }

    private List<GreenScanMappingConfiguration> createScanMappingConfiguration(List<Class<?>> annotationField, Map<String, Object> annotationValue) {
        List<GreenScanMappingConfiguration> list = new ArrayList<>();
        annotationField.forEach(field -> {
            Object obj = annotationValue.get(field.getSimpleName());
            if(Objects.nonNull(obj)) {
                list.add(GreenScanMappingConfiguration.builder()
                        .annotation(field.getSimpleName())
                        .value(obj)
                        .build());
            }
        });
        return list;
    }

    private void saveAll(List<GreenScanMapping> greenScanMappings) {
        scanConfigurationDao.deleteAll();
        scanConfigurationDao.save(greenScanMappings);
    }
}
