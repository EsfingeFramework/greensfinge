package net.sf.esfinge.greenframework.mock.dao;

import net.sf.esfinge.greenframework.annotation.GreenReturnWhenSwitchOff;
import net.sf.esfinge.greenframework.mock.entity.User;

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
