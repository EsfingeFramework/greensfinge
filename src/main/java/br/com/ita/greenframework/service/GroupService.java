package br.com.ita.greenframework.service;

import br.com.ita.greenframework.annotation.GreenIgnore;

public class GroupService {

    @GreenIgnore(strValue = "Mock Value")
    public String doSomething(String test) {
        String value = "GruopService - doSomething - " + test;
        System.out.println(value);
        return value;
    }

    @GreenIgnore(ignore = true)
    public String doSomething2() {
        String value = "GruopService - doSomething2";
        System.out.println(value);
        return value;
    }

    @GreenIgnore(numericValue = 5374)
    public String doSomething3(Integer test) {
        String value = "GruopService - doSomething3 - "+test;
        System.out.println(value);
        return value;
    }

    public String doSomething4(Integer test) {
        String value = "GruopService - doSomething4 - "+test;
        System.out.println(value);
        return value;
    }
}
