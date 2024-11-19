package br.com.ita.greenframework.main;

import br.com.ita.greenframework.configuration.GreenConfigurationFacade;
import br.com.ita.greenframework.configuration.GreenFactory;
import br.com.ita.greenframework.dto.GreenConfiguration;
import br.com.ita.greenframework.dto.GreenDefaultConfiguration;
import br.com.ita.greenframework.dto.GreenNumberConfiguration;
import br.com.ita.greenframework.dto.GreenOptionalConfiguration;
import br.com.ita.greenframework.service.UserService;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {
        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        facade.getConfigurations().forEach(System.out::println);

        for (int i = 0; i < 4; i++) {
            GreenDefaultConfiguration config1 =  GreenOptionalConfiguration.builder()
                    .ignore(new Random().nextBoolean())
                    .numberDefaultValue(5612)
                    .configurationKey("keyGroupService")
                    .build();

            GreenDefaultConfiguration config2 =  GreenOptionalConfiguration.builder()
                    .ignore(new Random().nextBoolean())
                    .configurationKey("keyProfileService")
                    .strDefaultValue("Professor Guerra")
                    .build();
            GreenDefaultConfiguration config3 =  GreenNumberConfiguration.builder()
                    .configurationKey("keyNumber")
                    .value(23)
                    .build();

            facade.setGeneralConfiguration(config1);
            facade.setGeneralConfiguration(config2);
            facade.setGeneralConfiguration(config3);

            UserService service = GreenFactory.greenify(UserService.class);
            service.createUser();
        }
    }
}
