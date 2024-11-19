package br.com.ita.greenframework.main;

import br.com.ita.greenframework.configurations.GreenConfigurationFacade;
import br.com.ita.greenframework.configurations.GreenFactory;
import br.com.ita.greenframework.dto.GreenOptionalConfiguration;
import br.com.ita.greenframework.service.UserService;

public class Main {

    public static void main(String[] args) throws Exception {
        //Varrer o codigo identificar o @GreenOptional e executar abaixo

        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        facade.getConfigurations().forEach(System.out::println);

        for (int i = 0; i < 4; i++) {
            facade.setGeneralConfiguration(new GreenOptionalConfiguration(true, "keyGroupService"));
            facade.setGeneralConfiguration(new GreenOptionalConfiguration(true, "keyProfileService"));

            UserService service = GreenFactory.greenify(UserService.class);
            service.createUser();
        }
    }
}
