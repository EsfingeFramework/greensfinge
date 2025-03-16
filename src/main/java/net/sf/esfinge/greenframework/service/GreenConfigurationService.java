package net.sf.esfinge.greenframework.service;

import net.sf.esfinge.greenframework.util.GreenEnvironment;
import net.sf.esfinge.greenframework.configuration.esfinge.dto.ClassContainer;
import net.sf.esfinge.greenframework.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.dao.GreenFactoryDao;
import net.sf.esfinge.greenframework.dao.contract.GreenConfigurationDao;
import net.sf.esfinge.greenframework.dao.memory.GreenConfigurationDaoImpl;
import net.sf.esfinge.greenframework.dto.project.GreenConfiguration;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.metadata.AnnotationReader;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GreenConfigurationService {

    private final GreenConfigurationDao configurationDao = GreenFactoryDao.getInstance().create(GreenConfigurationDaoImpl.class);

    public List<GreenConfiguration> getConfigurationsInProject() {
        List<GreenConfiguration> configurations = configurationDao.findConfigurations();

        if(configurations.isEmpty()) {
            configurations = scanForConfigurations();
        }

        return configurations;
    }

    private List<GreenConfiguration> scanForConfigurations() {
        String scanPackage = GreenEnvironment.getPackage();
        log.info("Scanning package: {}",scanPackage);

        List<GreenConfiguration> configs = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(scanPackage)
                .scan()) {

            searchForAnnotation(scanResult, configs);
        }
        return configs;
    }

    private void searchForAnnotation(ScanResult scanResult, List<GreenConfiguration> configs) {
        scanResult.getAllClasses().forEach(classInfo -> {
            try {
                Class<?> clazz = Class.forName(classInfo.getName());

                AnnotationReader reader = new AnnotationReader();
                ClassContainer containerField = reader.readingAnnotationsTo(clazz, ClassContainer.class);

                if(!containerField.getFields().isEmpty()) {
                    for (ContainerField field : containerField.getFields()) {
                        configs.add(GreenConfiguration.builder()
                                .className(clazz.getName())
                                .fieldName(field.getAttributeName())
//                                .annotation(field.getAnnotationField())
                                .configurationValues(field.getAnnotationValue())
                                .build());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        saveAll(configs);
    }

    private void saveAll(List<GreenConfiguration> greenConfigurations) {
        configurationDao.save(greenConfigurations);
    }

}
