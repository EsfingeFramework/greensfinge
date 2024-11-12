package br.com.ita.greenframework.service;

import br.com.ita.greenframework.annotation.GreenIgnore;

public class UserServiceImpl implements UserService {

    @GreenIgnore
    public void create() {
        System.out.println("UserServiceImpl create");
    }

    @GreenIgnore
    public void update() {
        System.out.println("UserServiceImpl update");
    }

}
