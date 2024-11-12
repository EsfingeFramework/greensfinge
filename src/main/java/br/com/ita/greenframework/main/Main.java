package br.com.ita.greenframework.main;

import br.com.ita.greenframework.configurations.GreenFactory;
import br.com.ita.greenframework.service.UserService;

public class Main {

    public static void main(String[] args) {
        //Varrer o codigo identificar o @GreenOptional e executar abaixo
        UserService service = GreenFactory.greenify(UserService.class);

//        GreenConfigurationFacade facade = new GreenConfigurationFacade();
//        facade.getConfigurations();

//        facade.setGeneralConfiguration(null);

        service.createUser();
    }
}
