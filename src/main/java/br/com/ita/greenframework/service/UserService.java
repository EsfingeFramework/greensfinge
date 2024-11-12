package br.com.ita.greenframework.service;

import br.com.ita.greenframework.annotations.GreenOptional;

public class UserService {

    @GreenOptional(configurationkey = "groupService")
    private GroupService groupService;

    public void createUser() {
        System.out.println("Init UserService - createUser");
        groupService.doSomething0();
        String return1 = groupService.doSomething("Test");
        String return2 = groupService.doSomething2();
        String return3 = groupService.doSomething3(1);
        String return4 = groupService.doSomething4(2);
        Integer return5 = groupService.doSomething5(4);

        System.out.println("******************************************************");
        System.out.println("******************************************************");
        System.out.println("******************************************************");

        System.out.println("Call: groupService.doSomething0() - return: void");
        System.out.println("Call: groupService.doSomething() - return: "+return1);
        System.out.println("Call: groupService.doSomething2() - return: "+return2);
        System.out.println("Call: groupService.doSomething3(1) - return: "+return3);
        System.out.println("Call: groupService.doSomething4(2) - return: "+return4);
        System.out.println("Call: groupService.doSomething5(4) - return: "+return5);
        System.out.println("Finish UserService - createUser");
    }
}
