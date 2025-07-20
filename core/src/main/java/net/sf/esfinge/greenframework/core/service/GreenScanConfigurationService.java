package net.sf.esfinge.greenframework.core.service;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ClassContainer;
import net.sf.esfinge.greenframework.core.configuration.esfinge.dto.ContainerField;
import net.sf.esfinge.greenframework.core.dao.GreenFactoryDao;
import net.sf.esfinge.greenframework.core.dao.contract.GreenScanConfigurationDao;
import net.sf.esfinge.greenframework.core.dao.memory.GreenScanConfigurationDaoImpl;
import net.sf.esfinge.greenframework.core.dto.project.ScanGreenConfiguration;
import net.sf.esfinge.greenframework.core.util.GreenEnvironment;
import net.sf.esfinge.metadata.AnnotationReader;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GreenScanConfigurationService {

    private final GreenScanConfigurationDao scanConfigurationDao = GreenFactoryDao.getInstance().create(GreenScanConfigurationDaoImpl.class);

    public List<ScanGreenConfiguration> getConfigurationsInProject() {
        List<ScanGreenConfiguration> configurations = scanConfigurationDao.findConfigurations();

        if (configurations.isEmpty()) {
            configurations = scanForConfigurations();
        }

        return configurations;
    }

    private List<ScanGreenConfiguration> scanForConfigurations() {
        String scanPackage = GreenEnvironment.getPackage();
        log.info("Scanning package: {}", scanPackage);

        List<ScanGreenConfiguration> configs = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(scanPackage)
                .scan()) {

            searchForAnnotation(scanResult, configs);
        }
        return configs;
    }

    private void searchForAnnotation(ScanResult scanResult, List<ScanGreenConfiguration> configs) {
        scanResult.getAllClasses().forEach(classInfo -> {
            try {
                Class<?> clazz = Class.forName(classInfo.getName());

                AnnotationReader reader = new AnnotationReader();
                ClassContainer containerField = reader.readingAnnotationsTo(clazz, ClassContainer.class);

                if (!containerField.getFields().isEmpty()) {
                    for (ContainerField field : containerField.getFields()) {
                        configs.add(ScanGreenConfiguration.builder()
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

    private void saveAll(List<ScanGreenConfiguration> scanGreenConfigurations) {
        scanConfigurationDao.save(scanGreenConfigurations);
    }
}
