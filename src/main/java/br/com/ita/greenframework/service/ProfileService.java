package br.com.ita.greenframework.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProfileService {

    public String doSomething6(String otherTest) {
        String value = "ProfileService - doSomething6 - " + otherTest;
        log.info(value);
        return value;
    }
}
