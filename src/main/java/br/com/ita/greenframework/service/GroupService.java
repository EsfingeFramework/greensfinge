package br.com.ita.greenframework.service;

public class GroupService {

    public String doSomething(String test) {
        String value = "GruopService - doSomething - " + test;
        System.out.println(value);
        return value;
    }

    public void doSomething0() {
        String value = "GruopService - doSomething0 - ";
        System.out.println(value);
    }

    public String doSomething2() {
        String value = "GruopService - doSomething2";
        System.out.println(value);
        return value;
    }

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
