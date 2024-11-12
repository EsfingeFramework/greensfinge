package br.com.ita.greenframework.service;

import br.com.ita.greenframework.annotations.GreenOptional;

public class UserService {

    @GreenOptional(configurationkey = "groupService")
    private GroupService groupService;

    public void createUser() {
        System.out.println("Init UserService - createUser");
        groupService.doSomething0();
        groupService.doSomething2();
        groupService.doSomething("Test");
        groupService.doSomething3(1);
        groupService.doSomething4(2);
        System.out.println("Finish UserService - createUser");
    }
}
