package br.com.ita.greenframework.service;

import br.com.ita.greenframework.annotation.GreenDefault;
import br.com.ita.greenframework.annotation.GreenNumberConfig;
import br.com.ita.greenframework.annotation.GreenOptional;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class UserService {

    @GreenOptional(configurationKey = @GreenDefault(configurationKey = "keyGroupService"))
    private GroupService groupService = new GroupService();

    @GreenOptional(configurationKey = @GreenDefault(configurationKey = "keyProfileService"), strDefaultValue = "Teste do Thiago")
    private ProfileService profileService = new ProfileService();

    @GreenNumberConfig(configurationKey = @GreenDefault(configurationKey = "keyNumber"))
    private Integer numberTest = 50;

    public void createUser() {
        log.info("Init UserService - createUser\n\n");
        groupService.doSomething0();
        String return1 = groupService.doSomething("Test");
        String return2 = groupService.doSomething2();
        String return3 = groupService.doSomething3(1);
        String return4 = groupService.doSomething4(2);
        Integer return5 = groupService.doSomething5(4);
        String return6 = profileService.doSomething6("OtherTest");

        log.info("\n\n******************************************************");
        log.info("******************************************************");
        log.info("******************************************************\n\n");

        log.info("Call: groupService.doSomething0() - return: void");
        log.info("Call: groupService.doSomething(\"Test\") - return: "+return1);
        log.info("Call: groupService.doSomething2() - return: "+return2);
        log.info("Call: groupService.doSomething3(1) - return: "+return3);
        log.info("Call: groupService.doSomething4(2) - return: "+return4);
        log.info("Call: groupService.doSomething5(4) - return: "+return5);
        log.info("Call: profileService.doSomething6(\"OtherTest\") - return: "+return6);
        log.info("Call: UserService.numberTest - return: "+numberTest);

        log.info("Finish UserService - createUser\n\n");
    }

}
