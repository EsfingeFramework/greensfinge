package br.com.ita.greenframework.main;

import br.com.ita.greenframework.service.GroupService;

public class Main {

    public static void main(String[] args) {
        GroupService service = new GroupService();

        String return1 = service.doSomething("Making a test");
        String return2 = service.doSomething2();
        String return3 = service.doSomething3(50);
        String return4 = service.doSomething4(65);

        System.out.println("****************************");
        System.out.println("Call: service.doSomething(\"Making a test\") -> return: "+return1);
        System.out.println("Call: service.doSomething2() -> return: "+return2);
        System.out.println("Call: service.doSomething3(50) -> return: "+return3);
        System.out.println("Call: service.doSomething4(65) -> return: "+return4);
    }
}
