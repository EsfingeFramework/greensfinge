package br.com.ita.greenframework.main;

import br.com.ita.greenframework.configurations.GreenConfigurationFacade;
import br.com.ita.greenframework.configurations.GreenFactory;
import br.com.ita.greenframework.dto.OptionalConfiguration;
import br.com.ita.greenframework.service.UserService;

public class Main {

    public static void main(String[] args) {
        //Varrer o codigo identificar o @GreenOptional e executar abaixo

        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        facade.getConfigurations().forEach(System.out::println);

        facade.setGeneralConfiguration(new OptionalConfiguration(true, "groupService"));

        UserService service = GreenFactory.greenify(UserService.class);
        service.createUser();

        facade.setGeneralConfiguration(new OptionalConfiguration(false, "groupService"));
        UserService serviceA = GreenFactory.greenify(UserService.class);

        serviceA.createUser();
    }
}
