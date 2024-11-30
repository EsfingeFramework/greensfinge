package br.com.ita.greenframework.service;

import br.com.ita.greenframework.util.GreenEnvironment;
import br.com.ita.greenframework.configuration.esfinge.dto.ClassContainer;
import br.com.ita.greenframework.configuration.esfinge.dto.ContainerField;
import br.com.ita.greenframework.dao.GreenFactoryDao;
import br.com.ita.greenframework.dao.contract.GreenConfigurationDao;
import br.com.ita.greenframework.dao.memory.GreenConfigurationDaoImpl;
import br.com.ita.greenframework.dto.project.GreenConfiguration;
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
                                .annotation(field.getAnnotationField())
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
