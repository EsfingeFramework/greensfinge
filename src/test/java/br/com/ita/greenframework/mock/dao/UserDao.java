package br.com.ita.greenframework.mock.dao;

import br.com.ita.greenframework.annotation.GreenReturnWhenSwitchOff;
import br.com.ita.greenframework.mock.entity.User;

public class UserDao {

    @GreenReturnWhenSwitchOff
    public User getUser() {
        return new User();
    }

    @GreenReturnWhenSwitchOff(strValue = "{\"name\":\"Mock Annotation\",\"countLogin\":2,\"profile\":\"Mock Profile Annotation\"}")
    public User getUserWithAnnotation() {
        return new User();
    }
}
