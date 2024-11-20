package br.com.ita.greenframework.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GroupService {

    public String doSomething(String test) {
        String value = "GruopService - doSomething - " + test;
        log.info(value);
        return value;
    }

    public void doSomething0() {
        String value = "GruopService - doSomething0 - ";
        log.info(value);
    }

    public String doSomething2() {
        String value = "GruopService - doSomething2";
        log.info(value);
        return value;
    }

    public String doSomething3(Integer test) {
        String value = "GruopService - doSomething3 - "+test;
        log.info(value);
        return value;
    }

    public String doSomething4(Integer test) {
        String value = "GruopService - doSomething4 - "+test;
        log.info(value);
        return value;
    }

    public Integer doSomething5(Integer test) {
        String value = "GruopService - doSomething5 - "+test;
        log.info(value);
        return 10;
    }

}
