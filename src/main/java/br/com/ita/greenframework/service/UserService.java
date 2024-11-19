package br.com.ita.greenframework.service;

import br.com.ita.greenframework.annotation.GreenDefault;
import br.com.ita.greenframework.annotation.GreenNumberConfig;
import br.com.ita.greenframework.annotation.GreenOptional;

public class UserService {

    @GreenOptional(configurationKey = @GreenDefault(configurationKey = "keyGroupService"))
    private GroupService groupService = new GroupService();

    @GreenOptional(configurationKey = @GreenDefault(configurationKey = "keyProfileService"), strDefaultValue = "Teste do Thiago")
    private ProfileService profileService = new ProfileService();

    @GreenNumberConfig(configurationKey = @GreenDefault(configurationKey = "keyNumber"))
    private Integer numberTest = 50;

    public void createUser() {
        System.out.println("Init UserService - createUser\n\n");
        groupService.doSomething0();
        String return1 = groupService.doSomething("Test");
        String return2 = groupService.doSomething2();
        String return3 = groupService.doSomething3(1);
        String return4 = groupService.doSomething4(2);
        Integer return5 = groupService.doSomething5(4);
        String return6 = profileService.doSomething6("OtherTest");

        System.out.println("\n\n******************************************************");
        System.out.println("******************************************************");
        System.out.println("******************************************************\n\n");

        System.out.println("Call: groupService.doSomething0() - return: void");
        System.out.println("Call: groupService.doSomething(\"Test\") - return: "+return1);
        System.out.println("Call: groupService.doSomething2() - return: "+return2);
        System.out.println("Call: groupService.doSomething3(1) - return: "+return3);
        System.out.println("Call: groupService.doSomething4(2) - return: "+return4);
        System.out.println("Call: groupService.doSomething5(4) - return: "+return5);
        System.out.println("Call: profileService.doSomething6(\"OtherTest\") - return: "+return6);
        System.out.println("Call: UserService.numberTest - return: "+numberTest);

        System.out.println("Finish UserService - createUser\n\n");
    }

}
