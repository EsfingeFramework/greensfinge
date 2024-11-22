package br.com.ita.greenframework.main;

import br.com.ita.greenframework.configuration.facade.GreenConfigurationFacade;
import br.com.ita.greenframework.configuration.GreenFactory;
import br.com.ita.greenframework.configuration.facade.GreenMetricFacade;
import br.com.ita.greenframework.dto.annotation.GreenNumberConfiguration;
import br.com.ita.greenframework.dto.annotation.GreenOptionalConfiguration;
import br.com.ita.greenframework.service.tests.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        makeConfigurations(facade);

        UserService service = GreenFactory.greenify(UserService.class);
        service.createUser();

        GreenMetricFacade metricFacade = new GreenMetricFacade();
        metricFacade.getSavedEnergy().forEach(metric -> log.info(metric.toString()));
    }

    private static void makeConfigurations(GreenConfigurationFacade facade) {
        facade.getConfigurations().forEach(config -> log.info(config.toString()));

        facade.setGeneralConfiguration(GreenOptionalConfiguration.builder()
                .ignore(true)
                .numberDefaultValue(5612)
                .configurationKey("keyGroupService")
                .build());

        facade.setGeneralConfiguration(GreenOptionalConfiguration.builder()
                .ignore(true)
                .configurationKey("keyProfileService")
                .strDefaultValue("Teacher Guerra")
                .build());

        facade.setGeneralConfiguration(GreenNumberConfiguration.builder()
                .configurationKey("keyBeginCountPrimes")
                .value(2)
                .build());

        facade.setGeneralConfiguration(GreenNumberConfiguration.builder()
                .configurationKey("keyEndCountPrimes")
                .value(25_000_000)
                .build());

        facade.setGeneralConfiguration(GreenOptionalConfiguration.builder()
                .ignore(true)
                .numberDefaultValue(222)
                .configurationKey("keyMathService")
                .build());

    }
}
